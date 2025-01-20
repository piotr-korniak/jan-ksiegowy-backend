package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.payment.dto.PaymentDto;
import pl.janksiegowy.backend.finances.payment.dto.PaymentMap;
import pl.janksiegowy.backend.finances.settlement.Settlement;
import pl.janksiegowy.backend.finances.settlement.SettlementFacade;
import pl.janksiegowy.backend.finances.settlement.SettlementFactory;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementMap;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.payment.PaymentRegisterQueryRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;

import java.util.UUID;

@Log4j2

@Service
@AllArgsConstructor
public class PaymentMigrationService {

    private final ClearingQueryRepository clearings;
    private final SettlementQueryRepository settlements;
    private final PaymentRegisterQueryRepository registers;
    private final PaymentFacade payment;
    private final SettlementFacade settlement;
    private final PaymentQueryRepository payments;
    private final PeriodFacade period;
    private final DataLoader loader;

    private Payment save( PaymentDto source) {
        //return payment.approve( payment.save( source));
        return payment.save( source);
    }

    private void book( Payment doc) {
        payment.approve( doc);
    }

    private Settlement save( SettlementMap source) {
        return settlement.save( source);
    }

    public String init() {

        for( String[] clearing: loader.readData( "clearings.csv")) {
            if (clearing[0].startsWith("---"))
                continue;

            var amount= Util.toBigDecimal( clearing[4], 2);
            var taxNumber= Util.toTaxNumber( clearing[1]);
            var date= Util.toLocalDate( clearing[3]);

            period.findMonthPeriodOrAdd( date);

            if( amount.signum() > 0) {
                if (!clearings.existReceivable( clearing[2], taxNumber, Util.toLocalDate( clearing[3]))) {
                    System.err.println( "Znaleziony 1: "+ clearing[2]);
                    settlements.findByNumberAndEntityTaxNumber( clearing[2], taxNumber)
                            .ifPresent( settlementDto -> {
                                System.err.println( "Znaleziony 2: "+ settlementDto.getNumber());
                                registers.findByCode( clearing[0]).ifPresent( register -> {

                                    var receivable= new SettlementMap( settlementDto);
                                    var pay= new PaymentMap(
                                            payments.findByEntityTaxNumberAndIssueDateAndRegisterCodeAndType(
                                                            taxNumber, date, register.getCode(), PaymentType.R)
                                                    .orElseGet(() -> PaymentDto.create()
                                                            .documentId( UUID.randomUUID())
                                                            .type( PaymentType.R)
                                                            .entity( receivable.getEntity())
                                                            .issueDate( date)
                                                            .register( register)))
                                            .add( amount);

                                    book( save( pay.add( receivable.add( ClearingDto.create()
                                            .date( date)
                                            .amount( amount)
                                            .payable( pay.getDocumentId())
                                            .receivable( receivable.getSettlementId())))
                                    ));
                                    save( receivable);
                                });
                            });
                }

            } else {
                if (!clearings.existPayable( clearing[2], taxNumber, Util.toLocalDate( clearing[3]))) {
                    System.err.println( "Znaleziony 1: "+ clearing[2]);
                    settlements.findByNumberAndEntityTaxNumber( clearing[2], taxNumber)
                            .ifPresent( settlementDto -> {
                                registers.findByCode(clearing[0]).ifPresent(register -> {
                                    System.err.println( "Znaleziony 2: "+ settlementDto.getNumber());
                                    var payable= new SettlementMap( settlementDto);
                                    var pay= new PaymentMap(
                                            payments.findByEntityTaxNumberAndIssueDateAndRegisterCodeAndType(
                                                            taxNumber, date, register.getCode(), PaymentType.E)
                                                    .orElseGet(() -> PaymentDto.create()
                                                            .documentId(UUID.randomUUID())
                                                            .type(PaymentType.E)
                                                            .entity(payable.getEntity())
                                                            .issueDate(date)
                                                            .register(register)))
                                            .add(amount.abs());

                                    book(save(pay.add(payable.add(ClearingDto.create()
                                            .date(date)
                                            .amount(amount.abs())
                                            .payable(payable.getSettlementId())
                                            .receivable(pay.getDocumentId())))
                                    ));
                                    save(payable);
                                });
                            });
                }
            }
        }
        log.warn( "Payments migration complete!");
        return "Payments migration complete!";
    }

}
