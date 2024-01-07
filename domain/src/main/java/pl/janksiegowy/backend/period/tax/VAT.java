package pl.janksiegowy.backend.period.tax;

public enum VAT {
    No {
        @Override public boolean isVat() {
            return Boolean.FALSE;
        }
    },
    Yes {
        @Override public boolean isVat() {
            return Boolean.TRUE;
        }
    };

    public abstract boolean isVat() ;

}
