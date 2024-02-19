package pl.janksiegowy.backend.invoice;

public enum InvoiceType {
    S { // Sales invoice
        @Override public <T> T accept( InvoiceTypeVisitor<T> visitor) {
            return visitor.visitSalesInvoice();
        }
    },
    P { // Purchase invoice
        @Override public <T> T accept( InvoiceTypeVisitor<T> visitor) {
            return visitor.visitPurchaseInvoice();
        }
    };
    public abstract <T> T accept( InvoiceTypeVisitor<T> visitor);

    public interface InvoiceTypeVisitor<T> {
        T visitSalesInvoice();
        T visitPurchaseInvoice();
    }
}
