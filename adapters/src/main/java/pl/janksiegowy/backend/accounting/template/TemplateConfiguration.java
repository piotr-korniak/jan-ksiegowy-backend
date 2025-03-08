package pl.janksiegowy.backend.accounting.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.account.AccountRepository;
import pl.janksiegowy.backend.register.RegisterRepository;
import pl.janksiegowy.backend.shared.MigrationService;

@Configuration
public class TemplateConfiguration {

    @Bean
    TemplateFacade templateFacade(final TemplateRepository templateRepository,
                                  final TemplateQueryRepository templates,
                                  final RegisterRepository registerRepository,
                                  final AccountRepository accounts,
                                  final MigrationService migrationService) {
        return new TemplateFacade( templateRepository, templates,
                new TemplateFactory( registerRepository, new TemplateLineFactory( accounts)), migrationService);
    }
}
