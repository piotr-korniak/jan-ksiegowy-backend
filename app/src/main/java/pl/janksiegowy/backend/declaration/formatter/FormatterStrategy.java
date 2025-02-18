package pl.janksiegowy.backend.declaration.formatter;

import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

public interface FormatterStrategy<T, C> {

    boolean isApplicable( TaxType taxType);
    LocalDate getDateApplicable();

    T format( Period period, C result);

}
