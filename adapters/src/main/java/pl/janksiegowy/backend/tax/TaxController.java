package pl.janksiegowy.backend.tax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.subdomain.TenantController;

@TenantController
@RequestMapping( "/v2/tax/{period}")
public class TaxController {

    private final TaxCalculatorManager taxCalculatorManager;

    @Autowired
    public TaxController( TaxCalculatorManager taxCalculatorManager) {
        this.taxCalculatorManager= taxCalculatorManager;
    }

    @GetMapping("/calculateAllTaxes")
    public ResponseEntity calculateAllTaxes() {

        taxCalculatorManager.calculate();

        return ResponseEntity.ok( "Calculate All Tax!");
    }
}
