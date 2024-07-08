package pl.janksiegowy.backend.tax;

import pl.janksiegowy.backend.period.MonthPeriod;

import java.time.LocalDate;

public interface TaxCalculatorBundle {

    void calculateTaxes( MonthPeriod period);
    boolean isApplicable( LocalDate date);
    LocalDate getDateApplicable();
}
