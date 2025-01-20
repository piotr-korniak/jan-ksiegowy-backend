package pl.janksiegowy.backend.statement.formatter;

import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

public interface FormatterStrategy<T> {

    boolean isApplicable( TaxType taxType);
    LocalDate getDateApplicable();

    T format( Period period, Interpreter result);

}
