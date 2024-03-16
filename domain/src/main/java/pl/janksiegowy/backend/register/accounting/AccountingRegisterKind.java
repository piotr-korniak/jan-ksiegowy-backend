package pl.janksiegowy.backend.register.accounting;

import pl.janksiegowy.backend.register.payment.PaymentRegisterKind;

public enum AccountingRegisterKind {
    A {
        @Override public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitAssetRegister();
        }
    } // assets
    ,
    /**
     * Decree
     */
    D {
        @Override public <T> T accept( AccountingRegisterTypeVisitor<T> visitor) {
            return visitor.visitDecreeRegister();
        }
    },
    H {
        @Override
        public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitShareholdingRegister();
        }
    } // shareholdings
    ,
    I {
        @Override
        public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitSalesInvoiceRegister();
        }
    } // sales invoice
    ,
    R {
        @Override
        public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitPayrollRegister();
        }
    } // payrolls
    ,
    T {
        @Override
        public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitStatementRegister();
        }
    } // statement
    ,
    V {
        @Override
        public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitPurchaseInvoiceRegister();
        }
    } // purchase invoice
    ,
    Y {
        @Override
        public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitPaymentRegister();
        }
    } // payment
    ;

    public abstract <T> T accept( AccountingRegisterTypeVisitor<T> visitor );

    public interface AccountingRegisterTypeVisitor<T> {
        T visitSalesInvoiceRegister();

        T visitPurchaseInvoiceRegister();

        T visitPaymentRegister();

        T visitPayrollRegister();

        T visitAssetRegister();

        T visitShareholdingRegister();

        T visitStatementRegister();

        T visitDecreeRegister();

    }

}
