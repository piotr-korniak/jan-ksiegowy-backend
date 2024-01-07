package pl.janksiegowy.backend.period;

import java.time.LocalDate;
import java.util.Optional;

public interface PeriodRepository {

    Optional<MonthPeriod> findMonthByDate( LocalDate date);
    Optional<QuaterPeriod> findQuarterByDate( LocalDate date);
    Optional<AnnualPeriod> findAnnualByDate( LocalDate date);

    Period save( Period period);
}
