package pl.janksiegowy.backend.declaration.calculate;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

public interface CalculateStrategy<T>{

    T calculate( MonthPeriod period);

    boolean isApplicable( TaxType taxType);
    LocalDate getDateApplicable();
}
