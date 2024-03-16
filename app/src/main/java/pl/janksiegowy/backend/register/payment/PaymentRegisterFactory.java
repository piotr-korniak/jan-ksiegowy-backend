package pl.janksiegowy.backend.register.payment;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.register.Register;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType.PaymentRegisterTypeVisitor;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

@AllArgsConstructor
public class PaymentRegisterFactory implements PaymentRegisterTypeVisitor<PaymentRegister>{

    private final NumeratorFacade numerators;

    public PaymentRegister from( RegisterDto source) {
        return update( source, PaymentRegisterType.valueOf( source.getType()).accept( this)
                .setAccountNumber( numerators.increment( NumeratorCode.RE, source.getType())));
    }

    public PaymentRegister update( RegisterDto source) {
        return update( source, PaymentRegisterType.valueOf( source.getType()).accept( this)
                .setRegisterId( source.getRegisterId())
                .setAccountNumber( source.getAccountNumber()));
    }

    public PaymentRegister update( RegisterDto source, Register register) {
        return (PaymentRegister) register
                .setCode( source.getCode())
                .setName( source.getName());
    }

    @Override public PaymentRegister visitBankAccount() {
        return new BankAccount();
    }

    @Override public PaymentRegister visitCashDesk() {
        return new CashDesk();
    }
}
