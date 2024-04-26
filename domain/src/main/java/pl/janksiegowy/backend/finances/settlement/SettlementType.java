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

    /** VAT Statement */
    V { @Override public <T> T accept( SettlementTypeVisitor<T> visitor ) {
            return visitor.visitVatStatement();
    }},

    /** CIT Statement */
    C { @Override public <T> T accept( SettlementTypeVisitor<T> visitor ) {
        return visitor.visitCitStatement();
    }},

    /** PIT Statement */
    T { @Override public <T> T accept( SettlementTypeVisitor<T> visitor ) {
        return visitor.visitPitStatement();
    }},

    /** DRA Statement */
    D { @Override public <T> T accept( SettlementTypeVisitor<T> visitor ) {
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

    }
}
