package pl.janksiegowy.backend.payment;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.payment.dto.ClearingDto;
import pl.janksiegowy.backend.payment.dto.PaymentDto;
import pl.janksiegowy.backend.register.PaymentRegisterQueryRepository;
import pl.janksiegowy.backend.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class PaymentInitializer {

    private final ClearingQueryRepository clearings;
    private final SettlementQueryRepository settlements;
    private final PaymentRegisterQueryRepository registers;
    private final PaymentFacade facade;
    private final DataLoader loader;

    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "--- dd.MM.yyyy");

    public void init() {

        loader.readData( "clearings.txt")
                .forEach( clearing-> {
                    var amount= Util.toBigDecimal( clearing[4], 2);
                    var taxNumber= Util.toTaxNumber( clearing[1]);

                    if( amount.signum()> 0) {
                        if( !clearings.existReceivable( clearing[2], taxNumber, Util.toLocalDate( clearing[3]))) {
                            settlements.findByNumberAndEntityTaxNumber( clearing[2], taxNumber)
                                    .ifPresent( settlementDto-> {
                                        registers.findByCode( clearing[0]).ifPresent( register-> {

                                            var receivable= settlements.findByNumberAndEntityTaxNumber( clearing[2], taxNumber)
                                                    .orElseThrow();

                                            var payable= facade.save( PaymentDto.create()
                                                    .type( PaymentType.R)
                                                    .amount( amount)
                                                    .date( Util.toLocalDate( clearing[3]))
                                                    .register( register));

                                            facade.save( ClearingDto.create()
                                                    .date( Util.toLocalDate( clearing[3]))
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

                                        var receivable= facade.save( PaymentDto.create()
                                                .type( PaymentType.S)
                                                .amount( amount.negate())
                                                .date( Util.toLocalDate( clearing[3]))
                                                .register( register));

                                        facade.save( ClearingDto.create()
                                                .date( Util.toLocalDate( clearing[3]))
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
