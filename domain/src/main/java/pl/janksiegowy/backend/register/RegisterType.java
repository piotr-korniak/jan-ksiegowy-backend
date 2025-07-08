package pl.janksiegowy.backend.register;

import pl.janksiegowy.backend.register.payment.PaymentRegisterType;

public enum RegisterType {

    /** Accounting Registers */
    R {
        @Override public <T, S> T accept( RegisterTypeVisitor<T, S> visitor, S source) {
            return visitor.visitAccountingRegister( source);
        }
    },

    A {
        @Override public <T, S> T accept( RegisterTypeVisitor<T, S> visitor, S source) {
            return visitor.visitBankAccount( source);
        }
    },

    D {
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

        default T visitAccountingRegister( S source) {
            throw new UnsupportedOperationException( "Accounting register not supported");
        }

        default T visitBankAccount( S source) {
            throw new UnsupportedOperationException( "Bank account not supported");
        }

        default T visitCashDesk( S source) {
            throw new UnsupportedOperationException( "Cash desk not supported");
        }

        default T visitSalesRegister( S source) {
            throw new UnsupportedOperationException( "Sales register not supported");
        }

        default T visitPurchaseRegister( S source) {
            throw new UnsupportedOperationException( "Purchase register not supported");
        }
    }

}
