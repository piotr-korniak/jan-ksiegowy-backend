package pl.janksiegowy.backend.tax;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.time.LocalDate;
import java.util.List;

public interface TaxCalculatorBundle {

    

    List<StatementDto> calculateTaxes( MonthPeriod period);
    LocalDate getDateApplicable();
}
