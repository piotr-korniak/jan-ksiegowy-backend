package pl.janksiegowy.backend.period;

public enum PeriodType {
    A { // Annual
        @Override public <T> T accept( PeriodTypeVisitor<T> visitor) {
            return visitor.visitAnnualPeriod();
        }
    },
    Q { // Quarter
        @Override public <T> T accept( PeriodTypeVisitor<T> visitor) {
            return visitor.visitQuarterPeriod();
        }
    },
    M { // Month
        @Override public <T> T accept( PeriodTypeVisitor<T> visitor) {
            return visitor.visitMonthPeriod();
        }
    };

    public abstract <T> T accept( PeriodTypeVisitor<T> visitor);

    public interface PeriodTypeVisitor<T> {
        T visitAnnualPeriod();
        T visitQuarterPeriod();
        T visitMonthPeriod();
    }
}
