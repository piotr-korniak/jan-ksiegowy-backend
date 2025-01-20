package pl.janksiegowy.backend.accounting.decree;

public enum DecreeType {
    D { // Settlement decree
        @Override public <T> T accept( DecreeTypeVisitor<T> visitor) {
            return visitor.visitDocumentDecree();
        }
    },
    S {
        @Override public <T> T accept( DecreeTypeVisitor<T> visitor) {
            return visitor.visitStatementDecree();
        }
    };

    public abstract <T> T accept( DecreeTypeVisitor<T> visitor);

    public interface DecreeTypeVisitor<T> {
        T visitDocumentDecree();
        T visitStatementDecree();
    }
}
