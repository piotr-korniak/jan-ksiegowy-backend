package pl.janksiegowy.backend.statement.builder;

import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.statement.SpecificStatement;
import pl.janksiegowy.backend.statement.dto.StatementDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

public class Factory_JPK_VAT implements SpecificStatement<StatementDto> {
    @Override
    public StatementDto build(Metric metric, MonthPeriod period) {
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
