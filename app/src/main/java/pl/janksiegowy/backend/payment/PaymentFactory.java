package pl.janksiegowy.backend.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import pl.janksiegowy.backend.payment.Payment.PaymentVisitor;
import pl.janksiegowy.backend.payment.PaymentType.PaymentTypeVisitor;
import pl.janksiegowy.backend.payment.dto.PaymentDto;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType.PaymentRegisterTypeVisitor;
import pl.janksiegowy.backend.settlement.PaymentSettlement;
import pl.janksiegowy.backend.settlement.SettlementKind;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
public class PaymentFactory {

    private final PaymentRegisterRepository registers;
    private final NumeratorFacade numerators;

    public Payment from( PaymentDto source ) {
        return source.getType().accept( new PaymentTypeVisitor<Payment>() {
                    @Override
                    public Payment visitPaymentReceipt() {
                        return new PaymentReceipt().setSettlement( (PaymentSettlement)
                                new PaymentSettlement()
                                        .setKind( SettlementKind.C )
                                        .setCt( source.getAmount() ) );
                    }

                    @Override
                    public Payment visitPaymentSpend() {
                        return new PaymentSpend().setSettlement( (PaymentSettlement)
                                new PaymentSettlement()
                                        .setKind( SettlementKind.D )
                                        .setDt( source.getAmount() ) );
                    }

                } ).accept( new PaymentVisitor<Payment>() {
                    @Override
                    public Payment visit( PaymentReceipt payment ) {
                        return registers.findByCode( source.getRegister().getCode() )
                                .map( register -> register.getType().accept( new PaymentRegisterTypeVisitor<Payment>() {
                                    @Override
                                    public Payment visitBankAccount() {
                                        return payment.setNumber( numerators
                                                .increment( "BP", source.getPaymentDate(), register.getCode() ) );
                                    }

                                    @Override
                                    public Payment visitCashDesk() {
                                        return payment.setNumber( numerators
                                                .increment( "KP", source.getPaymentDate(), register.getCode() ) );
                                    }
                                } ).setRegister( register ) )
                                .orElseThrow();
                    }

                    @Override
                    public Payment visit( PaymentSpend payment ) {
                        return registers.findByCode( source.getRegister().getCode() )
                                .map( register -> register.getType().accept( new PaymentRegisterTypeVisitor<Payment>() {
                                    @Override
                                    public Payment visitBankAccount() {
                                        return payment.setNumber( numerators
                                                .increment( "BW", source.getPaymentDate(), register.getCode() ) );
                                    }

                                    @Override
                                    public Payment visitCashDesk() {
                                        return payment.setNumber( numerators
                                                .increment( "KW", source.getPaymentDate(), register.getCode() ) );
                                    }
                                } ) )
                                .orElseThrow();
                    }

                } ).setPaymentId( Optional.ofNullable( source.getPaymentId() )
                        .orElse( UUID.randomUUID() ) )
                .setDate( source.getPaymentDate() );


    }
}
