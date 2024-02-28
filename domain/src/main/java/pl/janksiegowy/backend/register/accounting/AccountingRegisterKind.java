package pl.janksiegowy.backend.register.accounting;

import pl.janksiegowy.backend.register.payment.PaymentRegisterKind;

public enum AccountingRegisterKind {
    A { // Accounting Register - dummy
        @Override public <T> T accept( AccountingRegisterKindVisitor<T> visitor ) {
            return visitor.visitAccountingRegister();
        }
    };

    public abstract <T> T accept( AccountingRegisterKindVisitor<T> visitor);

    public interface AccountingRegisterKindVisitor<T> {
        T visitAccountingRegister();

    }
}
