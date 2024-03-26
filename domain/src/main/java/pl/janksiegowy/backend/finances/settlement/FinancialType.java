package pl.janksiegowy.backend.finances.settlement;

public enum FinancialType {
    /** Charge Settlement */
    C { @Override public <T> T accept( FinancialTypeVisitor<T> visitor ) {
            return visitor.visitChargeSettlement();
    }},
    /** Fee Settlement */
    F { @Override public <T> T accept( FinancialTypeVisitor<T> visitor ) {
            return visitor.visitFeeSettlement();
    }},
    /** Note Settlement */
    N { @Override public <T> T accept( FinancialTypeVisitor<T> visitor ) {
            return visitor.visitNoteSettlement();
    }};

    public abstract <T> T accept( FinancialTypeVisitor<T> visitor);

    public interface FinancialTypeVisitor<T> {
        T visitChargeSettlement();
        T visitFeeSettlement();
        T visitNoteSettlement();
    }
}
