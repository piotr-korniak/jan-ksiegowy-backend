package pl.janksiegowy.backend.register.accounting;

public enum AccountingRegisterType {
    I { // sales invoice
        @Override public <T> T accept( AccountingRegisterTypeVisitor<T> visitor) {
            return visitor.visitSalesInvoiceRegister();
        }
    },
    V { // purchase invoice
        @Override public <T> T accept( AccountingRegisterTypeVisitor<T> visitor) {
            return visitor.visitPurchaseInvoiceRegister();
        }
    },
    Y { // payment
        @Override public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitPaymentRegister();
        }
    },
    R { // payrolls
        @Override public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitPayrollRegister();
        }
    },
    A { // assets
        @Override public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitAssetRegister();
        }
    },
    H { // shareholdings
        @Override public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitShareholdingRegister();
        }
    },
    T { // statement
        @Override public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitStatementRegister();
        }
    },
    D { // decree
        @Override public <T> T accept( AccountingRegisterTypeVisitor<T> visitor ) {
            return visitor.visitDecreeRegister();
        }
    };

    public abstract <T> T accept( AccountingRegisterTypeVisitor<T> visitor);

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
