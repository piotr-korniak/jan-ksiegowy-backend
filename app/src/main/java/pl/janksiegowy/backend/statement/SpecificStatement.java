package pl.janksiegowy.backend.statement;

import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

public interface SpecificStatement <T>{

    T build(Metric metric, MonthPeriod period);

    boolean isApplicable( TaxType taxType);
    LocalDate getDateApplicable();
}
