package pl.janksiegowy.backend.accounting.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.shared.MigrationService;

@Configuration
public class AccountConfiguration {

    @Bean
    AccountFacade accountFacade( final AccountRepository accountRepository,
                                 final AccountQueryRepository accounts,
                                 final MigrationService migrationService) {
        return new AccountFacade( new AccountFactory( accountRepository), accountRepository, accounts, migrationService);
    }
}
