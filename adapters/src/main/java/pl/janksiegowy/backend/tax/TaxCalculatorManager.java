package pl.janksiegowy.backend.tax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.period.MonthPeriod;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class TaxCalculatorManager {

    private final List<TaxCalculatorBundle> taxCalculatorBundles;

    @Autowired
    public TaxCalculatorManager( List<TaxCalculatorBundle> taxCalculatorBundles) {
        this.taxCalculatorBundles= taxCalculatorBundles;
    }

    public void calculate(){
        System.out.println( "Tax calculator list...");
        for (TaxCalculatorBundle taxCalculatorBundle : taxCalculatorBundles) {
            taxCalculatorBundle.calculateTaxes( new MonthPeriod());
        }

        var date= LocalDate.of( 2017,9,1);

        taxCalculatorBundles.stream()
                .filter(d -> !d.getDateApplicable().isAfter( date))
                .max( Comparator.comparing(TaxCalculatorBundle::getDateApplicable))
                .ifPresent( taxCalculatorBundle -> {
                    taxCalculatorBundle.calculateTaxes( new MonthPeriod());
                });


    }
}
