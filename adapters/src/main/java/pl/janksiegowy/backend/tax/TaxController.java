package pl.janksiegowy.backend.tax;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.statement.StatementFacade;
import pl.janksiegowy.backend.subdomain.TenantController;

@TenantController
@RequestMapping( "/v2/tax/{periodId}")
@AllArgsConstructor
public class TaxController {

    private final PeriodRepository periods;
    private final TaxFacade taxFacade;
    private final StatementFacade statementFacade;

    @GetMapping
    public ResponseEntity<String> calculateAllTaxes( @PathVariable String periodId) {

        periods.findMonthById( periodId)
                .ifPresent( taxFacade::calculate);
        return ResponseEntity.ok( "Calculated all Taxes for " + periodId);
    }
}
