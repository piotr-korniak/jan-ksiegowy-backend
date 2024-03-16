package pl.janksiegowy.backend.statement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.subdomain.TenantController;

@TenantController
@RequestMapping( "/v2/cit/{period}")
public class CitController {

    private final CitApproval approval;

    public CitController( final DecreeLineQueryRepository lines,
                          final PeriodRepository periods) {
        this.approval= new CitApproval( lines, periods);
    }

    @PostMapping
    public ResponseEntity approval( @PathVariable String period) {

        approval.approval( period);

        return ResponseEntity.ok(  "CIT approval: "+ period);
    }
}
