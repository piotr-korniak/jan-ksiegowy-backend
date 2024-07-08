package pl.janksiegowy.backend.tax;

import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.period.MonthPeriod;

import java.time.LocalDate;

@Service
public class TaxCalculatorBundleJPK implements TaxCalculatorBundle {

    private final LocalDate dateApplicable= LocalDate.of(2018, 1, 1);

    @Override
    public void calculateTaxes( MonthPeriod period) {
        System.err.println( "Tax calculation JPK");
    }

    @Override
    public boolean isApplicable(LocalDate date) {
        return !date.isAfter( dateApplicable );
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
