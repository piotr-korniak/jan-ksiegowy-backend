package pl.janksiegowy.backend.finances.payment;

public enum PaymentType {

    /** Payment Receipt */
    R { @Override public <T> T accept( PaymentTypeVisitor<T> visitor) {
            return visitor.visitPaymentReceipt();
        }},

    /**
     * Payment Expense
     */
    E { @Override public <T> T accept( PaymentTypeVisitor<T> visitor) {
            return visitor.visitPaymentExpense();
        }};


    public abstract <T> T accept( PaymentTypeVisitor<T> visitor);

    public interface PaymentTypeVisitor<T> {
        T visitPaymentReceipt();
        T visitPaymentExpense();
    }
}
