package pl.janksiegowy.backend.register;

public enum RegisterType {
    S { // Sales
        @Override public <T> T accept( RegisterTypeVisitor<T> visitor) {
            return visitor.visitSalesVatRegister();
        }
    },
    P { // Purchase
        @Override public <T> T accept( RegisterTypeVisitor<T> visitor) {
            return visitor.visitPurchaseVatRegister();
        }
    };

    public abstract <T> T accept( RegisterTypeVisitor<T> visitor);

    public interface RegisterTypeVisitor<T> {
        T visitSalesVatRegister();
        T visitPurchaseVatRegister();
    }
}
