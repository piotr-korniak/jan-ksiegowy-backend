package pl.janksiegowy.backend.accounting.template;

public enum FinanceFunction {

    WartoscZobowiazania {
        @Override public <T> T accept( NoteFunctionVisitor<T> visitor) {
            return visitor.visitWartoscZobowiazania();
        }
    },
    WartoscNaleznosci {
        @Override public <T> T accept( NoteFunctionVisitor<T> visitor) {
            return visitor.visitWartoscNaleznosci();
        }
    };

    public abstract <T> T accept( NoteFunctionVisitor<T> visitor);

    public interface NoteFunctionVisitor<T> {
        T visitWartoscZobowiazania();
        T visitWartoscNaleznosci();
    }
}
