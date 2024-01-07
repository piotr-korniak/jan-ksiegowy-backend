package pl.janksiegowy.backend.period.tax;

public enum CIT {
    No {
        @Override public boolean isCit() {
            return Boolean.FALSE;
        }
    },
    Yes {
        @Override public boolean isCit() {
            return Boolean.TRUE;
        }
    };

    public abstract boolean isCit() ;

}
