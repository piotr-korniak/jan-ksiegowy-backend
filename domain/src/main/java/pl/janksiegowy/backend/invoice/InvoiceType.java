package pl.janksiegowy.backend.invoice;

public enum InvoiceType {

    /** Sales invoice */
    S { @Override public <T, S> T accept( InvoiceTypeVisitor<T, S> visitor, S source) {
            return visitor.visitSalesInvoice( source);
    }},

    /** Purchase invoice */
    P { @Override public <T, S> T accept( InvoiceTypeVisitor<T, S> visitor, S source) {
            return visitor.visitPurchaseInvoice( source);
    }};

    public abstract <T, S> T accept( InvoiceTypeVisitor<T, S> visitor, S source);

    public interface InvoiceTypeVisitor<T, S> {
        T visitSalesInvoice( S source);
        T visitPurchaseInvoice( S source);
    }
}
