package pl.janksiegowy.backend.accounting.decree;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.account.AccountFacade;
import pl.janksiegowy.backend.accounting.account.AccountRepository;
import pl.janksiegowy.backend.accounting.decree.factory.CloseMonthFactory;
import pl.janksiegowy.backend.accounting.decree.factory.PayslipSubFactory;
import pl.janksiegowy.backend.accounting.template.TemplateRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.RegisterRepository;
import pl.janksiegowy.backend.salary.PayslipRepository;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

@Configuration
public class DecreeConfiguration {

    @Bean
    DecreeFacade decreeFacade(final RegisterRepository registerRepository,
                              final TemplateRepository templates,
                              final DecreeRepository decrees,
                              final AccountRepository accounts,
                              final AccountFacade account,
                              final PeriodFacade period,
                              final NumeratorFacade numerators,
                              final ClearingRepository clearings,
                              final ClearingQueryRepository clearingsQuery,
                              final PayslipSubFactory payslipSubFactory,
                              final CloseMonthFactory closeMonthFactory,
                              final PayslipRepository payslipRepository) {
        return new DecreeFacade( new DecreeFactory( templates, registerRepository,
                        new DecreeLineFactory( accounts, account),
                        period, numerators, clearings, clearingsQuery, payslipSubFactory, closeMonthFactory, payslipRepository),
                decrees);
    }
}
