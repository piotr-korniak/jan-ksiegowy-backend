package pl.janksiegowy.backend.statement.factory;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.statement.formatter.dto.FormatterDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

public interface FactoryStrategy <T>{

    T create( MonthPeriod period, Interpreter calculation, FormatterDto formatted);

    boolean isApplicable( TaxType taxType);
    LocalDate getDateApplicable();

}
