package pl.janksiegowy.backend.register.payment;

public enum PaymentRegisterKind {

    P { // Payment Register - dummy
        @Override public <T> T accept( PaymentRegisterKindVisitor<T> visitor ) {
            return visitor.visitPaymentRegister();
        }
    };

    public abstract <T> T accept( PaymentRegisterKindVisitor<T> visitor);

    public interface PaymentRegisterKindVisitor<T> {
        T visitPaymentRegister();

    }
}
