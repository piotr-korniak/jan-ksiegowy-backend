package pl.janksiegowy.backend.finances.settlement;

public enum SettlementType {

    /** Payment Receipt */
    R { @Override public <T> T accept( SettlementTypeVisitor<T> visitor ) {
            return visitor.visitPaymentReceipt();
        }},

    /** Payment Expense */
    E { @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
            return visitor.visitPaymentExpense();
    }},


    /** Sales Invoice */
    S { @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
        return visitor.visitSalesInvoice();
    }},

    /** Purchase Invoice */
    P { @Override public <T> T accept( SettlementTypeVisitor<T> visitor ) {
            return visitor.visitPurchaseInvoice();
    }},


    /** Issued Note */
    I { @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
        return visitor.visitIssuedNote();
    }},

    /** Received Note */
    N { @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
        return visitor.visitReceiveNote();
    }},

    /** fixMe VAT Statement */
    V { @Override public <T> T accept( SettlementTypeVisitor<T> visitor ) {
            return visitor.visitVatStatement();
    }},

    /** fixMe CIT Statement */
    C { @Override public <T> T accept( SettlementTypeVisitor<T> visitor ) {
        return visitor.visitCitStatement();
    }},

    /** Tax Declaration */
    T { @Override public <T> T accept( SettlementTypeVisitor<T> visitor ) {
        return visitor.visitPitStatement();
    }},

    /** fixMe DRA Statement */
    K { @Override public <T> T accept(SettlementTypeVisitor<T> visitor ) {
        return visitor.visitDraStatement();
    }},


    /** Employ salary */
    Y { @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
            return visitor.visitPayslipSettlement();
    }},

    /** Levy Charge */
    L { @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
        return visitor.visitLevyCharge();
    }},

    /** Fee Charge */
    F { @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
            return visitor.visitFeeCharge();
    }},


    /** Acquisition and Sale of Shares */
    A {@Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
        return visitor.visitAcquisition();
    }},

    /** Disposal and Decreased of Share */
    D {@Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
        return visitor.visitDisposal();
    }};

    public abstract <T> T accept( SettlementTypeVisitor<T> visitor);

    public interface SettlementTypeVisitor<T> {

        T visitPaymentReceipt();
        T visitPaymentExpense();

        T visitPurchaseInvoice();
        T visitSalesInvoice();

        T visitReceiveNote();
        T visitIssuedNote();

        T visitVatStatement();
        T visitCitStatement();
        T visitPitStatement();
        T visitDraStatement();

        T visitPayslipSettlement();

        T visitLevyCharge();
        T visitFeeCharge();

        T visitAcquisition();
        T visitDisposal();

    }
}
