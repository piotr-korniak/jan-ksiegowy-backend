package pl.janksiegowy.backend.tax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TaxCalculatorManager {

    private final List<TaxCalculatorBundle> taxCalculatorBundles;

    @Autowired
    public TaxCalculatorManager( List<TaxCalculatorBundle> taxCalculatorBundles) {
        this.taxCalculatorBundles= taxCalculatorBundles;
    }

    public List<StatementDto> calculate( MonthPeriod period) {
        return taxCalculatorBundles.stream()
                .filter( d-> !d.getDateApplicable().isAfter( period.getEnd()))
                .max( Comparator.comparing( TaxCalculatorBundle::getDateApplicable))
                .map( bundle-> bundle.calculateTaxes( period))
                .orElseGet( ArrayList::new);
    }
}
