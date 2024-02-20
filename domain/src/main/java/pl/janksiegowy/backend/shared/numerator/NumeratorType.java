package pl.janksiegowy.backend.shared.numerator;

import pl.janksiegowy.backend.period.PeriodType;

public enum NumeratorType {
    Y {
        @Override public <T> T accept( NumeratorTypeVisitor<T> visitor) {
            return visitor.visitYearNumerator();
        }
    },
    M {
        @Override public <T> T accept( NumeratorTypeVisitor<T> visitor) {
            return visitor.visitMonthNumerator();
        }
    },
    E {
        @Override public <T> T accept( NumeratorTypeVisitor<T> visitor) {
            return visitor.visitEverNumerator();
        }
    };

    public abstract <T> T accept( NumeratorTypeVisitor<T> visitor);

    public interface NumeratorTypeVisitor<T> {
        T visitYearNumerator();
        T visitMonthNumerator();
        T visitEverNumerator();
    }
}
