package pl.janksiegowy.backend.tax;

import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaxCalculatorBundleJPK implements TaxCalculatorBundle {

    private final LocalDate dateApplicable= LocalDate.of(2018, 1, 1);

    @Override
    public List<StatementDto> calculateTaxes(MonthPeriod period) {
        System.err.println( "Tax calculation JPK");
        return null;
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
