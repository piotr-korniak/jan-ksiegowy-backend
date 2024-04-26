package pl.janksiegowy.backend.statement;

public enum StatementKind {

    /** Payable Statement */
    S { @Override public <T> T accept( StatementKindVisitor<T> visitor) {
            return visitor.visitPayableStatement();
    }},

    /** Register Statement */
    R { @Override public <T> T accept( StatementKindVisitor<T> visitor) {
            return visitor.visitRegisterStatement();
    }};

    public abstract <T> T accept( StatementKindVisitor<T> visitor);

    public interface StatementKindVisitor<T> {
        T visitPayableStatement();
        T visitRegisterStatement();
    }
}
