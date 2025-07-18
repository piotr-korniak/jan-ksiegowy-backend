package pl.janksiegowy.backend.declaration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.subdomain.DomainController;

@DomainController
@RequestMapping( "/v2/approval/{period}")
public class StatementController {

    private final StatementApproval approval;

    public StatementController( final PeriodRepository periods,
                                final StatementFacade statement,
                                final MetricRepository metric,
                                final StatementRepository statements,
                                final EntityQueryRepository entities,
                                final InvoiceLineQueryRepository invoiceLines,
                                final NumeratorFacade numerator) {
        this.approval= new StatementApproval( periods, statement, metric, statements, entities, invoiceLines, numerator);
    }

    @PostMapping
    public ResponseEntity approval( @PathVariable String period) {

        approval.approval( period);

        return ResponseEntity.ok(  "Approval period: "+ period);
    }
}
