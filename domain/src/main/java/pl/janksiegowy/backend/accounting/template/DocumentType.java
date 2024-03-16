package pl.janksiegowy.backend.accounting.template;

public enum DocumentType {

    /**
     * Sales Invoice
     */
    IS {
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor) {
            return visitor.visitSalesInvoice();
        }
    },
    /**
     * Purchase Invoice
     */
    IP {
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor) {
            return visitor.visitPurchaseInvoice();
        }
    },
    /**
     * Payment Receipt
     */
    PR {
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitPaymentReceipt();
        }
    },
    /**
     * Payment Spend
     */
    PS {
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitPaymentSpend();
        }
    },

    /*
    R { // payrolls
        @Override public <T> T accept( TemplateTypeVisitor<T> visitor ) {
            return visitor.visitPayroll();
        }
    },
    A { // assets
        @Override public <T> T accept( TemplateTypeVisitor<T> visitor ) {
            return visitor.visitAsset();
        }
    },
    H { // shareholdings
        @Override public <T> T accept( TemplateTypeVisitor<T> visitor ) {
            return visitor.visitShareholding();
        }
    },*/
    SV { // VAT statement
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitVatStatement();
        }
    },
    SC { // CIT statement
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitCitStatement();
        }
    },
    SP { // PIT statement
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitPitStatement();
        }
    },
    /**
     * National Insurance
     */
    SN {
        @Override
        public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return null;
        }
    }
    ;

    public abstract <T> T accept( DocumentTypeVisitor<T> visitor);

    public interface DocumentTypeVisitor<T> {
        T visitSalesInvoice();
        T visitPurchaseInvoice();

        T visitPaymentReceipt();
        T visitPaymentSpend();

        T visitVatStatement();
        T visitCitStatement();
        T visitPitStatement();

    }
}
