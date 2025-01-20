package pl.janksiegowy.backend.statement.factory;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.statement.calculate.CalculateStrategy;
import pl.janksiegowy.backend.statement.dto.StatementDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

public class Factory_JPK_VAT implements CalculateStrategy<StatementDto> {
    @Override
    public StatementDto calculate(MonthPeriod period) {
        return null;
    }

    @Override
    public boolean isApplicable( TaxType taxType) {
        return false;
    }

    @Override
    public LocalDate getDateApplicable() {
        return null;
    }
}
