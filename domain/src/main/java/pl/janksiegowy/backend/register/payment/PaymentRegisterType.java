package pl.janksiegowy.backend.register.payment;

public enum PaymentRegisterType {

    A { // Bank Account
        @Override public <T> T accept( PaymentRegisterTypeVisitor<T> visitor ) {
            return visitor.visitBankAccount();
        }

    },
    D { // Cash Desk
        @Override public <T> T accept( PaymentRegisterTypeVisitor<T> visitor ) {
            return visitor.visitCashDesk();
        }
    };

    public abstract <T> T accept( PaymentRegisterTypeVisitor<T> visitor);

    public interface PaymentRegisterTypeVisitor<T> {
        T visitBankAccount();
        T visitCashDesk();

    }
}
