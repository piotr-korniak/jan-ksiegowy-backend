package pl.janksiegowy.backend.accounting.decree;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.accounting.template.TemplateRepository;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterFactory;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;

@Configuration
public class DecreeConfiguration {

    @Bean
    DecreeFacade decreeFacade( final AccountingRegisterRepository registers,
                               final TemplateRepository templates,
                               final DecreeRepository decrees) {
        return new DecreeFacade( new AccountingRegisterFactory(), registers,
                new DecreeFactory( templates, registers), decrees);
    }
}
