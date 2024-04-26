package pl.janksiegowy.backend.accounting.template;

public enum TemplateType {

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
     * Payment Expanse
     */
    PE {
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitPaymentSpending();
        }
    },

    /**
     * Employee Payslip
     */
    EP {
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitEmployeePayslip();
        }
    },

    /** Notice Issued */
    NI {@Override public <T> T accept( DocumentTypeVisitor<T> visitor) {
            return visitor.visitNoteIssued();
    }},

    /** Notice Received */
    NR { @Override public <T> T accept( DocumentTypeVisitor<T> visitor) {
            return visitor.visitNoteReceived();
    }},
    /**
     * Charge Settlement
     */
    CH { @Override public <T> T accept( DocumentTypeVisitor<T> visitor) {
            return visitor.visitChargeSettlement();
    }},
    /**
     * Fee Settlement
     */
    FE { @Override public <T> T accept( DocumentTypeVisitor<T> visitor) {
            return visitor.visitFeeSettlement();
    }},
    /*
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
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return null;
        }
    }
    ;

    public abstract <T> T accept( DocumentTypeVisitor<T> visitor);

    public interface DocumentTypeVisitor<T> {
        T visitPaymentReceipt();
        T visitPaymentSpending();

        T visitSalesInvoice();
        T visitPurchaseInvoice();


        T visitVatStatement();
        T visitCitStatement();
        T visitPitStatement();

        T visitEmployeePayslip();

        T visitNoteIssued();
        T visitNoteReceived();
        T visitChargeSettlement();
        T visitFeeSettlement();

    }
}
