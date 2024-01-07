package pl.janksiegowy.backend.period.tax;

public enum PIT {
    No {
        @Override public boolean isPit() {
            return Boolean.FALSE;
        }
    },
    Yes {
        @Override public boolean isPit() {
            return Boolean.TRUE;
        }
    };

    public abstract boolean isPit() ;

}
