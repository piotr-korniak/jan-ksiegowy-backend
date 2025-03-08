package pl.janksiegowy.backend.register;

import pl.janksiegowy.backend.register.payment.PaymentRegisterType;

public enum RegisterType {

    /** Accounting Registers */
    A {
        @Override public <T, S> T accept( RegisterTypeVisitor<T, S> visitor, S source) {
            return visitor.visitAccountingRegister( source);
        }
    },

    B {
        @Override public <T, S> T accept( RegisterTypeVisitor<T, S> visitor, S source) {
            return visitor.visitBankAccount( source);
        }
    },

    C {
        @Override public <T, S> T accept(RegisterTypeVisitor<T, S> visitor, S source) {
            return visitor.visitCashDesk( source);
        }
    },

    S {
        @Override public <T, S> T accept(RegisterTypeVisitor<T, S> visitor, S source) {
            return visitor.visitSalesRegister( source);
        }
    },

    P {
        @Override public <T, S> T accept(RegisterTypeVisitor<T, S> visitor, S source) {
            return visitor.visitPurchaseRegister( source);
        }
    };

    public abstract <T, S> T accept( RegisterTypeVisitor<T, S> visitor, S source);

    public interface RegisterTypeVisitor<T, S> {

        T visitAccountingRegister( S source);

        T visitBankAccount( S source);
        T visitCashDesk( S source);

        T visitSalesRegister( S source);
        T visitPurchaseRegister( S source);
    }

}
