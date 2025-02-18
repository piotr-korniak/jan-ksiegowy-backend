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
import pl.janksiegowy.backend.finances.document.DocumentQueryRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementRepository;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterFactory;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;
import pl.janksiegowy.backend.salary.PayslipRepository;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

@Configuration
public class DecreeConfiguration {

    @Bean
    DecreeFacade decreeFacade(final AccountingRegisterRepository registers,
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
        return new DecreeFacade( new AccountingRegisterFactory(), registers,
                new DecreeFactory( templates, registers,
                        new DecreeLineFactory( accounts, account),
                        period, numerators, clearings, clearingsQuery, payslipSubFactory, closeMonthFactory, payslipRepository),
                decrees);
    }
}
