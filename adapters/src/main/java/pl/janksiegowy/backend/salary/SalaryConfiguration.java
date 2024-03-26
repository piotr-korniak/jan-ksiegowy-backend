package pl.janksiegowy.backend.salary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodRepository;

@Configuration
public class SalaryConfiguration {

    @Bean
    SalaryFacade salaryFacade( final PayslipRepository payslips,
                               final PeriodRepository periods,
                               final EntityRepository entities,
                               final DecreeFacade decree) {
        return new SalaryFacade( payslips, new SalaryFactory( periods, entities ), decree);
    }
}
