package pl.janksiegowy.backend.finances.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.payment.Payment.PaymentVisitor;
import pl.janksiegowy.backend.finances.payment.PaymentType.PaymentTypeVisitor;
import pl.janksiegowy.backend.finances.payment.dto.PaymentDto;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType.PaymentRegisterTypeVisitor;
import pl.janksiegowy.backend.finances.settlement.PaymentSettlement;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
public class PaymentFactory {

    private final PaymentRegisterRepository registers;
    private final NumeratorFacade numerators;
    private final PeriodRepository periods;
    private final EntityRepository entities;

    public Payment from( PaymentDto source ) {
        return source.getType().accept( new PaymentTypeVisitor<Payment>() {
                    @Override public Payment visitPaymentReceipt() {
                        return new PaymentReceipt()
                                .setSettlement( (PaymentSettlement)
                                new PaymentSettlement()
                                        .setKind( SettlementKind.C )
                                        .setCt( source.getAmount() ) );
                    }
                    @Override public Payment visitPaymentSpend() {
                        return new PaymentSpend().setSettlement( (PaymentSettlement)
                                new PaymentSettlement()
                                        .setKind( SettlementKind.D )
                                        .setDt( source.getAmount() ) );
                    }

                }).accept( new PaymentVisitor<Payment>() {
                    @Override public Payment visit( PaymentReceipt payment ) {
                        return registers.findByCode( source.getRegister().getCode() )
                            .map( register-> register.getType().accept( new PaymentRegisterTypeVisitor<Payment>() {
                                @Override public Payment visitBankAccount() {
                                    return payment.setNumber( Optional.ofNullable( source.getNumber())
                                            .orElseGet(()-> numerators.increment( NumeratorCode.BR,
                                                            register.getCode(), source.getPaymentDate())));
                                }
                                @Override public Payment visitCashDesk() {
                                    return payment.setNumber( Optional.ofNullable( source.getNumber())
                                            .orElseGet(()-> numerators.increment( NumeratorCode.CR,
                                                            register.getCode(), source.getPaymentDate())));
                                }
                            }).setRegister( register)
                            ).orElseThrow();
                    }

                    @Override public Payment visit( PaymentSpend payment ) {
                        return registers.findByCode( source.getRegister().getCode() )
                            .map( register-> register.getType().accept( new PaymentRegisterTypeVisitor<Payment>() {
                                @Override public Payment visitBankAccount() {
                                    return payment.setNumber( Optional.ofNullable( source.getNumber())
                                            .orElseGet(()-> numerators.increment( NumeratorCode.BS,
                                                            register.getCode(), source.getPaymentDate())));
                                }
                                @Override public Payment visitCashDesk() {
                                    return payment.setNumber( Optional.ofNullable( source.getNumber())
                                            .orElseGet(()-> numerators.increment( NumeratorCode.CS,
                                                            register.getCode(), source.getPaymentDate())));
                                }
                            }).setRegister( register)
                            ).orElseThrow();
                    }

                } ).setPaymentId( Optional.ofNullable( source.getPaymentId() )
                        .orElse( UUID.randomUUID() ) )
                .setDate( source.getPaymentDate())
                .setPeriod( periods.findMonthByDate( source.getPaymentDate()).orElseThrow())
                .setEntity( Optional.ofNullable( source.getEntity())
                        .map( entity->
                                entities.findByEntityIdAndDate( entity.getEntityId(), source.getPaymentDate()))
                        .orElseGet( null).get());

    }
}
