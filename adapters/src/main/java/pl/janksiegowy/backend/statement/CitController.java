package pl.janksiegowy.backend.statement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.subdomain.TenantController;

@TenantController
@RequestMapping( "/v2/cit/{period}")
public class CitController {

    private final CitApproval approval;

    public CitController( final DecreeLineQueryRepository lines,
                          final PeriodRepository periods,
                          final MetricRepository metrics,
                          final StatementFacade statement,
                          final StatementRepository statements,
                          final EntityQueryRepository entities,
                          final NumeratorFacade numerators ) {
        this.approval= new CitApproval( lines, periods, metrics, statement, statements, entities, numerators);
    }

    @PostMapping
    public ResponseEntity approval( @PathVariable String period) {
        return ResponseEntity.ok(  approval.approval( period).toString());
    }
}
