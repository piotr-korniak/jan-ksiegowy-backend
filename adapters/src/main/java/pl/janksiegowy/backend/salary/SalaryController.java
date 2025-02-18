package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.contract.ContractQueryRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.shared.indicator.IndicatorsProperties;
import pl.janksiegowy.backend.subdomain.TenantController;

import java.time.LocalDate;

@TenantController
@RequestMapping( "/v2/payroll/{periodId}")
@AllArgsConstructor
public class SalaryController {

    private final SalaryFacade salary;
    private final PeriodRepository periods;

    @PostMapping
    public ResponseEntity<String> calculate( @PathVariable String periodId) {
        return periods.findMonthById( periodId)
                .map( monthPeriod-> ResponseEntity.ok( salary.calculatePayslips( monthPeriod)))
                .orElseThrow(()-> new IllegalStateException( "Moth period "+ periodId+ " not found"));
    }
}

