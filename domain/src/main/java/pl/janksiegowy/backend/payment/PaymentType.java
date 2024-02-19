package pl.janksiegowy.backend.payment;

public enum PaymentType {

    R { // Payment Receipt
        @Override public <T> T accept( PaymentTypeVisitor<T> visitor) {
            return visitor.visitPaymentReceipt();
        }
    },
    S { // Payment Spend
        @Override public <T> T accept( PaymentTypeVisitor<T> visitor) {
            return visitor.visitPaymentSpend();
        }
    };

    public abstract <T> T accept( PaymentTypeVisitor<T> visitor);

    public interface PaymentTypeVisitor<T> {
        T visitPaymentReceipt();
        T visitPaymentSpend();
    }
}
