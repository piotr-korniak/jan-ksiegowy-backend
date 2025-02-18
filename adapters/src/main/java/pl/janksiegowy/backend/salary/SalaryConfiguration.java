package pl.janksiegowy.backend.salary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.accounting.decree.DecreeService;
import pl.janksiegowy.backend.contract.dto.ContractDto;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.salary.strategy.SalaryStrategy;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.util.List;

@Configuration
public class SalaryConfiguration {

    @Bean
    SalaryFacade salaryFacade( final ContractRepository contractRepository,
                               final SalaryService salaryService,
                               final DecreeService decreeService) {
        return new SalaryFacade( contractRepository, salaryService, decreeService);
    }
}
