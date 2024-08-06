package pl.janksiegowy.backend.tax;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.period.QuarterPeriod;

import java.time.Instant;
import java.time.LocalDate;

public interface SpecificItems <T> {

    T calculate( Period period);

    boolean isApplicable( TaxType taxType);
    LocalDate getDateApplicable();
}
