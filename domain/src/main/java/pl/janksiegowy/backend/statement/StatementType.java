package pl.janksiegowy.backend.statement;

public enum StatementType {

    V { // VAT
        @Override public <T> T accept( StatementTypeVisitor<T> visitor ) {
            return visitor.visitVatStatement();
        }
    },

    /** Invoice Register - JPK */
    R { @Override public <T> T accept( StatementTypeVisitor<T> visitor ) {
            return visitor.visitJpkStatement();
    }},

    /** CIT Statement */
    I { @Override public <T> T accept( StatementTypeVisitor<T> visitor ) {
            return visitor.visitCitStatement();
    }},

    T { // PIT
        @Override public <T> T accept( StatementTypeVisitor<T> visitor ) {
            return visitor.visitPitStatement();
        }
    },
    N { // Nation Insurance
        @Override public <T> T accept( StatementTypeVisitor<T> visitor ) {
            return visitor.visitZusStatement();
        }
    };

    public abstract <T> T accept( StatementTypeVisitor<T> visitor);

    public interface StatementTypeVisitor<T> {
        T visitVatStatement();
        T visitJpkStatement();
        T visitCitStatement();
        T visitPitStatement();
        T visitZusStatement();
    }
}
