package pl.janksiegowy.backend.statement.formatter;

import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

public interface TaxDeclarationFormatter {

    boolean isApplicable( TaxType taxType);
    LocalDate getDateApplicable();

    String format( Period period, Interpreter result);
    PatternId getPatternId();
}
