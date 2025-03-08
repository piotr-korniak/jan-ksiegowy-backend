package pl.janksiegowy.backend.register;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.shared.MigrationService;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

@Configuration
public class RegisterConfiguration {

    @Bean
    RegisterFacade registerFacade( final RegisterRepository registerRepository,
                                   final NumeratorFacade numeratorFacade,
                                   final MigrationService migrationService) {
        return new RegisterFacade( registerRepository, new RegisterFactory( numeratorFacade), migrationService);
    }
}
