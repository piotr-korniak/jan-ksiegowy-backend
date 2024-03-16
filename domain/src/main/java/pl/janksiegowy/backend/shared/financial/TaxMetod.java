package pl.janksiegowy.backend.shared.financial;

public enum TaxMetod {
    NL { // Normal, no change
        @Override public <T> T accept( TaxMetodVisitor<T> visitor) {
            return visitor.visitNL();
        }
    },
    V5 { // VAT 50%
        @Override public <T> T accept( TaxMetodVisitor<T> visitor) {
            return visitor.visitV5();
        }
    },
    C7 { // VAT 50%, CIT 75%
        @Override
        public <T> T accept( TaxMetodVisitor<T> visitor) {
            return visitor.visitC7();
        }

    },
    NC { // Not CIT
        @Override public <T> T accept( TaxMetodVisitor<T> visitor) {
            return visitor.visitNC();
        }
    };

    public abstract <T> T accept( TaxMetodVisitor<T> visitor);

    public interface TaxMetodVisitor<T> {
        T visitNL();
        T visitV5();
        T visitC7();
        T visitNC();
    }

}
