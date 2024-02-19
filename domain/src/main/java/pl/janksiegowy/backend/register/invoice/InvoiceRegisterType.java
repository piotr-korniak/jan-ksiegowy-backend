package pl.janksiegowy.backend.register.invoice;

public enum InvoiceRegisterType {
    S { // Sales
        @Override public <T> T accept( InvoiceRegisterTypeVisitor<T> visitor) {
            return visitor.visitSalesRegister();
        }
    },
    P { // Purchase
        @Override public <T> T accept( InvoiceRegisterTypeVisitor<T> visitor) {
            return visitor.visitPurchaseRegister();
        }
    };

    public abstract <T> T accept( InvoiceRegisterTypeVisitor<T> visitor);

    public interface InvoiceRegisterTypeVisitor<T> {
        T visitSalesRegister();
        T visitPurchaseRegister();
    }
}
