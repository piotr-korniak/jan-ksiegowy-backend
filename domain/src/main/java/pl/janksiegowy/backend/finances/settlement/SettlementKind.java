package pl.janksiegowy.backend.finances.settlement;

public enum SettlementKind {
    D { // Dt - Debit
        @Override public <T> T accept( SettlementKindVisitor<T> visitor ) {
            return visitor.visitDebit();
        }
    },
    C { // Cr - Credit
        @Override public <T> T accept( SettlementKindVisitor<T> visitor ) {
            return visitor.visitCredit();
        }
    };

    public abstract <T> T accept( SettlementKindVisitor<T> visitor);

    public interface SettlementKindVisitor<T> {
        T visitDebit();
        T visitCredit();
    }
}
