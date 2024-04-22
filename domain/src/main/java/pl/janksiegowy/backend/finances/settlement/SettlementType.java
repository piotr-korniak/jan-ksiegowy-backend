package pl.janksiegowy.backend.finances.settlement;

public enum SettlementType {

    /** Payment Receipt */
    R { @Override public <T> T accept( SettlementTypeVisitor<T> visitor ) {
            return visitor.visitReceiptSettlement();
        }},
    Y {
        @Override public <T> T accept( SettlementTypeVisitor<T> visitor ) {
            return visitor.visitInvoicePayable();
        }
    },
    /** Invoice settlement */
    I {
        @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
            return visitor.visitInvoiceSettlemnt();
        }
    },
    /**
     * Payment settlement
     */
    P {
        @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
            return visitor.visitPaymentSettlement();
        }
    },
    /**
     * Statement settlement
     */
    S {
        @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
            return visitor.visitStatementSettlement();
        }
    },
    /**
     *  Employ salary
     */
    E {
        @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
            return visitor.visitPayslipSettlement();
        }
    },
    /**
     * Charge settlement
     */
    C {
        @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
            return visitor.visitChargeSettlement();
        }
    },
    /**
     * Fee settlement
     */
    F {
        @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
            return visitor.visitFeeSettlement();
        }
    },
    /**
     * Note settlement
     */
    N {
        @Override public <T> T accept( SettlementTypeVisitor<T> visitor) {
            return visitor.visitNoteSettlement();
        }
    };

    public abstract <T> T accept( SettlementTypeVisitor<T> visitor);

    public interface SettlementTypeVisitor<T> {

        T visitReceiptSettlement();
        T visitInvoicePayable();
        T visitInvoiceSettlemnt();
        T visitPaymentSettlement();
        T visitStatementSettlement();
        T visitPayslipSettlement();
        T visitChargeSettlement();
        T visitFeeSettlement();
        T visitNoteSettlement();
    }
}
