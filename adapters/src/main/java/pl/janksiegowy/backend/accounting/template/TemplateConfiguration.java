package pl.janksiegowy.backend.accounting.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.account.AccountRepository;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;

@Configuration
public class TemplateConfiguration {

    @Bean
    TemplateFacade templateFacade( final TemplateRepository templates,
                                   final AccountingRegisterRepository registers,
                                   final AccountRepository accounts) {
        return new TemplateFacade( templates,
                new TemplateFactory( registers, new TemplateLineFactory( accounts)));
    }
}
