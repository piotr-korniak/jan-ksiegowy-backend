package pl.janksiegowy.backend.period;

import java.time.LocalDate;
import java.util.Optional;

public interface PeriodRepository {

    Optional<MonthPeriod> findMonthById( String id );

    Optional<MonthPeriod> findMonthByDate( LocalDate date);
    Optional<QuarterPeriod> findQuarterByDate( LocalDate date);
    Optional<AnnualPeriod> findAnnualByDate( LocalDate date);

    Period save( Period period);
    Optional<Period> findById( String periodId);
}
