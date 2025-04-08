package pl.janksiegowy.backend.tax;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.declaration.StatementFacade;
import pl.janksiegowy.backend.declaration.dto.StatementDto;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class TaxFacade {

    private final List<TaxBundle> taxCalculatorBundles;
    private final StatementFacade statementFacade;

    public List<StatementDto> calculate( MonthPeriod period) {

        System.err.println( "Calculate: "+ period.getId());
        taxCalculatorBundles.forEach( taxCalculatorBundle-> {
            System.err.println( "calculator: "+
                    taxCalculatorBundle.getDateApplicable()+ " : "+
                    taxCalculatorBundle.getClass().getSimpleName());
        });

        taxCalculatorBundles.stream()
                .filter( d-> !d.getDateApplicable().isAfter( period.getEnd()))
                .max( Comparator.comparing( TaxBundle::getDateApplicable))
                .ifPresent( bundle-> bundle.taxesToProcess( period)
                        .forEach( taxType-> statementFacade.process( period, taxType)));

        return null;
    }
}
