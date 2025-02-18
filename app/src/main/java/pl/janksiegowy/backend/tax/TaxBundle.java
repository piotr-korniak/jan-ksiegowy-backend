package pl.janksiegowy.backend.tax;

import pl.janksiegowy.backend.period.MonthPeriod;

import java.time.LocalDate;
import java.util.List;

public interface TaxBundle {

    List<TaxType> taxesToProcess( MonthPeriod monthPeriod);
    LocalDate getDateApplicable();
}
