package pl.janksiegowy.backend.accounting.template;

public enum TemplateType {

    /** Sales Invoice */
    IS {
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor) {
            return visitor.visitSalesInvoice();
        }
    },
    /** Purchase Invoice */
    IP {
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor) {
            return visitor.visitPurchaseInvoice();
        }
    },

    /** Payment Receipt */
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
    CL { @Override public <T> T accept(DocumentTypeVisitor<T> visitor) {
            return visitor.visitChargeLevy();
    }},
    /**
     * Fee Settlement
     */
    CF { @Override public <T> T accept( DocumentTypeVisitor<T> visitor) {
            return visitor.visitChargeFee();
    }},

    /** Acquisition of Shares */
    HA { @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitAcquiredShare();
        }
    },
    /** Disposal of Shares */
    HD { @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitDisposedShare();
        }
    },

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
            return visitor.visitDraStatement();
        }
    },
    /**
     * Month Close
     */
    MC {
        @Override public <T> T accept( DocumentTypeVisitor<T> visitor ) {
            return visitor.visitMonthClose();
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
        T visitDraStatement();

        T visitEmployeePayslip();

        T visitNoteIssued();
        T visitNoteReceived();
        T visitChargeLevy();
        T visitChargeFee();

        T visitAcquiredShare();
        T visitDisposedShare();

        T visitMonthClose();
    }
}
