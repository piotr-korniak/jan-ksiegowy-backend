package pl.janksiegowy.backend.stattement;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.statement.JpkVat;
import pl.janksiegowy.backend.statement.StatementApproval;
import pl.janksiegowy.backend.statement.StatementJpkFactory;
import pl.janksiegowy.backend.subdomain.TenantController;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamResult;
import org.springframework.oxm.Marshaller;

import java.io.IOException;

@TenantController
@RequestMapping( "/v2/approval/{period}")
public class StatementController {

    private final StatementApproval approval;

    public StatementController( final PeriodRepository periods,
                                final InvoiceLineQueryRepository lines) {
        this.approval= new StatementApproval( periods, lines);//, marshaller);
    }

    @PostMapping
    public ResponseEntity approval( @PathVariable String period) {

        approval.approval( period);

        return ResponseEntity.ok(  "Approval period: "+ period);
    }
}
