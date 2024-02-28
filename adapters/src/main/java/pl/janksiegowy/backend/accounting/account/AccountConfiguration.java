package pl.janksiegowy.backend.accounting.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {

    @Bean
    AccountFacade accountFacade( final AccountRepository accounts) {
        return new AccountFacade( new AccountFactory(), accounts);
    }
}
