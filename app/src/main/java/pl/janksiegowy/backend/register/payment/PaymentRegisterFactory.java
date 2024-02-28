package pl.janksiegowy.backend.register.payment;

import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType.PaymentRegisterTypeVisitor;

public class PaymentRegisterFactory {

    public PaymentRegister from( RegisterDto source) {
        return update( create( source), source);
    }

    public PaymentRegister update( RegisterDto source) {
        return update( (PaymentRegister) create( source)
                .setRegisterId( source.getRegisterId()), source);
    }

    public PaymentRegister update( PaymentRegister register, RegisterDto source) {
        return (PaymentRegister) register
                .setCode( source.getCode())
                .setName( source.getName());
    }

    private PaymentRegister create( RegisterDto source) {
        return PaymentRegisterType.valueOf( source.getType())
                .accept( new PaymentRegisterTypeVisitor<PaymentRegister>() {
                    @Override public PaymentRegister visitBankAccount() {
                        return new BankAccount();
                    }
                    @Override public PaymentRegister visitCashDesk() {
                        return new CashDesk();
                    }
                });
    }
}
