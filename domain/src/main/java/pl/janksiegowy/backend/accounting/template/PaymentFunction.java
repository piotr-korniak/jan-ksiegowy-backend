package pl.janksiegowy.backend.accounting.template;

public enum PaymentFunction {

    SplataNaleznosci {

        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor ) {
            return visitor.visitSplataNaleznosci();
        }
    },
    SplataZobowiazania {
        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor ) {
            return visitor.visitSplataZobowiazania();
        }
    };

    public abstract <T> T accept( PaymentFunctionVisitor<T> visitor);

    public interface PaymentFunctionVisitor<T> {
        T visitSplataNaleznosci();
        T visitSplataZobowiazania();
    }
}
