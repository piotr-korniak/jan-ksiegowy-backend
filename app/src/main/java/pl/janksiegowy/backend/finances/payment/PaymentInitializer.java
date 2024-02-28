package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.accounting.decree.DecreeFactory;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.payment.dto.PaymentDto;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.period.PeriodQueryRepository;
import pl.janksiegowy.backend.period.PeriodType;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.register.payment.PaymentRegisterQueryRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class PaymentInitializer {

    private final ClearingQueryRepository clearings;
    private final SettlementQueryRepository settlements;
    private final PaymentRegisterQueryRepository registers;
    private final PaymentFacade facade;
    private final PeriodQueryRepository periods;
    private final PeriodFacade period;
    private final DecreeFacade decree;
    private final DataLoader loader;

    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "--- dd.MM.yyyy");

    private Payment save( PaymentDto source) {
        var payment= facade.save( source);

        decree.book( payment);

        return payment;
    }

    public void init() {

        loader.readData( "clearings.txt")
                .forEach( clearing-> {
                    var amount= Util.toBigDecimal( clearing[4], 2);
                    var taxNumber= Util.toTaxNumber( clearing[1]);
                    var date= Util.toLocalDate( clearing[3]);

                    if( periods.findMonthByDate( date).isEmpty())
                        period.save( PeriodDto.create()
                                .type( PeriodType.M)
                                .begin( date));

                    if( amount.signum()> 0) {
                        if( !clearings.existReceivable( clearing[2], taxNumber, Util.toLocalDate( clearing[3]))) {
                            settlements.findByNumberAndEntityTaxNumber( clearing[2], taxNumber)
                                    .ifPresent( settlementDto-> {
                                        registers.findByCode( clearing[0]).ifPresent( register-> {

                                            var receivable= settlements.findByNumberAndEntityTaxNumber( clearing[2], taxNumber)
                                                    .orElseThrow();

                                            var payable= save( PaymentDto.create()
                                                    .type( PaymentType.R)
                                                    .entity( receivable.getEntity())
                                                    .amount( amount)
                                                    .date( date)
                                                    .register( register));

                                            facade.save( ClearingDto.create()
                                                    .date( date)
                                                    .amount( amount)
                                                    .payable( payable.getPaymentId())
                                                    .receivable( receivable.getId()));
                                        } );
                                    });
                        }

                    } else {
                        if( !clearings.existPayable( clearing[2], taxNumber, Util.toLocalDate( clearing[3]))) {
                            settlements.findByNumberAndEntityTaxNumber( clearing[2], taxNumber)
                                .ifPresent( settlementDto-> {
                                    registers.findByCode( clearing[0]).ifPresent( register-> {

                                        var payable= settlements.findByNumberAndEntityTaxNumber( clearing[2], taxNumber)
                                                .orElseThrow();

                                        var receivable= save( PaymentDto.create()
                                                .type( PaymentType.S)
                                                .entity( payable.getEntity() )
                                                .amount( amount.negate())
                                                .date( date)
                                                .register( register));

                                        facade.save( ClearingDto.create()
                                                .date( date)
                                                .amount( amount.negate())
                                                .payable( payable.getId())
                                                .receivable( receivable.getPaymentId()));
                                    } );
                                });
                        }
                    }
                });
    }
}
