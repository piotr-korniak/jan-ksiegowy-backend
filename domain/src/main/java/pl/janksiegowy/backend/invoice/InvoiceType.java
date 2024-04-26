package pl.janksiegowy.backend.invoice;

public enum InvoiceType {

    /** Sales invoice */
    S { @Override public <T> T accept( InvoiceTypeVisitor<T> visitor) {
            return visitor.visitSalesInvoice();
    }},

    /** Purchase invoice */
    P { @Override public <T> T accept( InvoiceTypeVisitor<T> visitor) {
            return visitor.visitPurchaseInvoice();
    }};

    public abstract <T> T accept( InvoiceTypeVisitor<T> visitor);

    public interface InvoiceTypeVisitor<T> {
        T visitSalesInvoice();
        T visitPurchaseInvoice();
    }
}
