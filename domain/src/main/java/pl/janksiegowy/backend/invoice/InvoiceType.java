package pl.janksiegowy.backend.invoice;

public enum InvoiceType {
    S { // A bill - commonly known as an Accounts Payable or supplier invoice
        @Override public <T> T accept( InvoiceTypeVisitor<T> visitor) {
            return visitor.visitPayable();
        }
    },
    C { // A sales invoice - commonly known as an Accounts Receivable or customer invoice
        @Override public <T> T accept( InvoiceTypeVisitor<T> visitor) {
            return visitor.visitReceivable();
        }
    };
    public abstract <T> T accept( InvoiceTypeVisitor<T> visitor);

    public interface InvoiceTypeVisitor<T> {
        T visitPayable();
        T visitReceivable();
    }
}
