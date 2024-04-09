package pl.janksiegowy.backend.accounting.decree;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.account.AccountFacade;
import pl.janksiegowy.backend.accounting.account.AccountRepository;
import pl.janksiegowy.backend.accounting.template.TemplateRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterFactory;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

@Configuration
public class DecreeConfiguration {

    @Bean
    DecreeFacade decreeFacade( final AccountingRegisterRepository registers,
                               final TemplateRepository templates,
                               final DecreeRepository decrees,
                               final AccountRepository accounts,
                               final AccountFacade account,
                               final PeriodFacade period,
                               final NumeratorFacade numerators,
                               final ClearingRepository clerings ) {
        return new DecreeFacade( new AccountingRegisterFactory(), registers,
                new DecreeFactory( templates, registers,
                        new DecreeLineFactory( accounts, account), period, numerators, clerings),
                decrees);
    }
}
