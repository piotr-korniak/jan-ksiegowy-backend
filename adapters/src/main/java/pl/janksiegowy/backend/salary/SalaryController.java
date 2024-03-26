package pl.janksiegowy.backend.salary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.salary.dto.ContractDto;
import pl.janksiegowy.backend.subdomain.TenantController;

@TenantController
@RequestMapping( "/v2/payroll/{periodId}")
public class SalaryController {

    private final SalaryFacade salary;
    private final PeriodRepository periods;
    private final ContractQueryRepository contracts;

    public SalaryController( final PeriodRepository periods,
                             final ContractQueryRepository contracts,
                             final SalaryFacade salary) {
        this.salary= salary;
        this.periods= periods;
        this.contracts= contracts;
    }

    @PostMapping
    public ResponseEntity calculate( @PathVariable String periodId) {
        return periods.findById( periodId).map( period -> {
            contracts.findBy( ContractDto.class)                   // fixme: major simplification
                    .forEach( contract -> salary.approval(
                            salary.calculate( contract, period.getEnd())));

            return ResponseEntity.ok(  ).build();
        })
                .orElseThrow();
    }
}

