package pl.janksiegowy.backend.register.payment;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.register.Register;
import pl.janksiegowy.backend.register.dto.BankAccountDto;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType.PaymentRegisterTypeVisitor;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class PaymentRegisterFactory implements PaymentRegisterTypeVisitor<PaymentRegister>{

    private final NumeratorFacade numerators;

    public PaymentRegister from( final BankAccountDto source) {
        return update( source, new BankAccount()
                .setNumber( source.getNumber())
                .setCurrency( source.getCurrency()));
    }

    public PaymentRegister from( RegisterDto source) {
        return update( source, PaymentRegisterType.valueOf( source.getType()).accept( this));
    }

    private PaymentRegister update( RegisterDto source, Register register) {
        return (PaymentRegister) register.setRegisterId( Optional.ofNullable( source.getRegisterId())
                        .orElseGet( UUID::randomUUID))
                .setAccountNumber( Optional.ofNullable( source.getAccountNumber())
                        .orElseGet(()-> numerators.increment( NumeratorCode.RE, source.getType())))
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
