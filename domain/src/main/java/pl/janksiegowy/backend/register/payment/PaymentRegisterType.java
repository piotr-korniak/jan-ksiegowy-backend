package pl.janksiegowy.backend.register.payment;

public enum PaymentRegisterType {

    B { // Bank Account
        @Override public <T> T accept( PaymentRegisterTypeVisitor<T> visitor ) {
            return visitor.visitBankAccount();
        }

    },
    C { // Cash Desk
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
