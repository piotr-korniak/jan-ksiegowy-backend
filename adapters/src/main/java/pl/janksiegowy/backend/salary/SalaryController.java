package pl.janksiegowy.backend.salary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.subdomain.TenantController;

@TenantController
@RequestMapping( "/v2/payroll/{period}")
public class SalaryController {

    @PostMapping
    public ResponseEntity calculate( @PathVariable String period) {

        return ResponseEntity.ok(  ).build();
    }
}

