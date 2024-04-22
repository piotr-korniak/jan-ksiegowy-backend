package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.payment.dto.PaymentDto;
import pl.janksiegowy.backend.finances.settlement.Settlement;
import pl.janksiegowy.backend.finances.settlement.SettlementFacade;
import pl.janksiegowy.backend.finances.settlement.SettlementFactory;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementMap;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.period.PeriodQueryRepository;
import pl.janksiegowy.backend.period.PeriodType;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.register.payment.PaymentRegisterQueryRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@AllArgsConstructor
public class PaymentInitializer {

    private final ClearingQueryRepository clearings;
    private final SettlementQueryRepository settlements;
    private final PaymentRegisterQueryRepository registers;
    private final PaymentFacade payment;
    private final SettlementFacade settlement;
    private final PaymentQueryRepository payments;
    private final PeriodQueryRepository periods;
    private final PeriodFacade period;
    private final DataLoader loader;

    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "--- dd.MM.yyyy");

    private Payment save( PaymentDto source) {
        return payment.save( source);
    }

    private Settlement save( SettlementMap source) {
        return settlement.save( source);
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

                                            var receivable= new SettlementMap(
                                                    settlements.findByNumberAndEntityTaxNumber( clearing[2], taxNumber)
                                                    .orElseThrow());

                                            var pay= payments.findByEntityTaxNumberAndDateAndRegisterCode(
                                                    taxNumber, date, register.getCode())
                                                            .orElseGet(()-> PaymentDto.create()
                                                                    .documentId( UUID.randomUUID())
                                                                    .type( PaymentType.R)
                                                                    .entity( receivable.getEntity())
                                                                    .date( date)
                                                                    .register( register));

                                            save( pay);             /** important, a number is assigned here */
                                            cleared( receivable, new SettlementMap( SettlementFactory.to( pay)
                                                            .amount( pay.getAmount().add( amount))),
                                                    date, amount);
                                        });
                                    });
                        }

                    } else {
                        if( !clearings.existPayable( clearing[2], taxNumber, Util.toLocalDate( clearing[3]))) {
                            settlements.findByNumberAndEntityTaxNumber( clearing[2], taxNumber)
                                .ifPresent( settlementDto-> {
                                    registers.findByCode( clearing[0]).ifPresent( register-> {

                                        var payable= new SettlementMap(
                                                settlements.findByNumberAndEntityTaxNumber( clearing[2], taxNumber)
                                                .orElseThrow());

                                        var pay= payments.findByEntityTaxNumberAndDateAndRegisterCode(
                                                        taxNumber, date, register.getCode())
                                                .orElseGet(()-> PaymentDto.create()
                                                        .documentId( UUID.randomUUID())
                                                        .type( PaymentType.E)
                                                        .entity( payable.getEntity())
                                                        .date( date)
                                                        .register( register));

                                        save( pay); /** important, a number is assigned here */
                                        cleared( new SettlementMap( SettlementFactory.to( pay)
                                                        .amount( pay.getAmount().add( amount.abs()))),
                                                payable, date, amount.abs());
                                    } );
                                });
                        }
                    }
                });
    }

    private void cleared( SettlementMap receivable, SettlementMap payable,
                          LocalDate date, BigDecimal amount) {
        receivable.add( payable.add( ClearingDto.create()
                .date( date)
                .amount( amount)
                .payable( payable.getSettlementId())
                .receivable( receivable.getSettlementId())));

        save( payable);
        save( receivable);
    }

}
