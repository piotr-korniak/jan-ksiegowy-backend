package pl.janksiegowy.backend.accounting.template;

public enum PaymentFunction {

    WplataNaleznosci {

        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor ) {
            return visitor.visitWplataNaleznosci();
        }
    },
    SplataZobowiazania {
        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor ) {
            return visitor.visitSplataZobowiazania();
        }
    };

    public abstract <T> T accept( PaymentFunctionVisitor<T> visitor);

    public interface PaymentFunctionVisitor<T> {
        T visitWplataNaleznosci();
        T visitSplataZobowiazania();
    }
}
