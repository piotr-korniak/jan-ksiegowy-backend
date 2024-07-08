package pl.janksiegowy.backend.accounting.template;

public enum SettlementFunction {

    WartoscZobowiazania {
        @Override public <T> T accept( SettlementFunctionVisitor<T> visitor) {
            return visitor.visitWartoscZobowiazania();
        }
    },
    WartoscNaleznosci {
        @Override public <T> T accept( SettlementFunctionVisitor<T> visitor) {
            return visitor.visitWartoscNaleznosci();
        }
    };

    public abstract <T> T accept( SettlementFunctionVisitor<T> visitor);

    public interface SettlementFunctionVisitor<T> {
        T visitWartoscZobowiazania();
        T visitWartoscNaleznosci();
    }
}
