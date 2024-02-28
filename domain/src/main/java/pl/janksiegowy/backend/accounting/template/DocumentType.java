package pl.janksiegowy.backend.accounting.template;

public enum DocumentType {

    IS { // sales invoice
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor) {
            return visitor.visitSalesInvoice();
        }
    },
    IP { // purchase invoice
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor) {
            return visitor.visitPurchaseInvoice();
        }
    },
    BR { // bank receipt
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitBankReceipt();
        }
    },
    BS { // bank spend
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitBankSpend();
        }
    },

    CR { // cash receipt
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitCashReceipt();
        }
    },
    CS { // cash spend
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitCashSpend();
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
    };

    public abstract <T> T accept( DocumentTypeVisitor<T> visitor);

    public interface DocumentTypeVisitor<T> {
        T visitSalesInvoice();
        T visitPurchaseInvoice();

        T visitVatStatement();
        T visitCitStatement();
        T visitPitStatement();

        T visitBankReceipt();

        T visitBankSpend();

        T visitCashReceipt();

        T visitCashSpend();
    }
}
