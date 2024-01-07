package pl.janksiegowy.backend.period.tax;

public enum JPK {
    No {
        @Override public boolean isJpk() {
            return Boolean.FALSE;
        }
    },
    Yes {
        @Override public boolean isJpk() {
            return Boolean.TRUE;
        }
    };

    public abstract boolean isJpk() ;

}
