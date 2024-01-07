package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.invoice.dto.InvoiceViewDto;
import pl.janksiegowy.backend.subdomain.TenantController;

import java.util.List;

//@RestController
@TenantController
@RequestMapping( "/v2/invoices")
@AllArgsConstructor
public class InvoiceController {

    private final InvoiceQueryRepository query;

    @GetMapping
    ResponseEntity<List<InvoiceViewDto>> list() {
        return ResponseEntity.ok( query.findBy( InvoiceViewDto.class));
    }
}
