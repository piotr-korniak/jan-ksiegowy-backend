package pl.janksiegowy.backend.salary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.salary.strategy.SalaryStrategy;

import java.util.List;

@Configuration
public class SalaryConfiguration {

    @Bean
    SalaryFacade salaryFacade( final PayslipRepository payslips,
                               final PeriodRepository periods,
                               final EntityRepository entities,
                               final DecreeFacade decree,
                               final List<SalaryStrategy> strategies) {
        return new SalaryFacade( payslips,
                new SalaryFactory( periods, entities, new PayslipLineFactory()),
                decree, strategies);
    }
}
