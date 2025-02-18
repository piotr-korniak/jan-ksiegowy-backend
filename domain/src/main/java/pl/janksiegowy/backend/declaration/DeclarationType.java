package pl.janksiegowy.backend.declaration;

public enum DeclarationType {

    /** VAT Statement */
    V { // VAT
        @Override public <T> T accept( DeclarationTypeVisitor<T> visitor ) {
            return visitor.visitVatDeclaration();
        }
    },

    /** Invoice Register - JPK */
    J { @Override public <T> T accept( DeclarationTypeVisitor<T> visitor ) {
            return visitor.visitJpkStatement();
    }},

    /** CIT Statement */
    C { @Override public <T> T accept( DeclarationTypeVisitor<T> visitor ) {
            return visitor.visitCitStatement();
    }},

    P { // PIT
        @Override public <T> T accept( DeclarationTypeVisitor<T> visitor ) {
            return visitor.visitPitStatement();
        }
    },
    D { // Nation Insurance
        @Override public <T> T accept( DeclarationTypeVisitor<T> visitor ) {
            return visitor.visitDraStatement();
        }
    };

    public abstract <T> T accept( DeclarationTypeVisitor<T> visitor);

    public interface DeclarationTypeVisitor<T> {
        T visitVatDeclaration();
        T visitJpkStatement();
        T visitCitStatement();
        T visitPitStatement();
        T visitDraStatement();
    }
}
