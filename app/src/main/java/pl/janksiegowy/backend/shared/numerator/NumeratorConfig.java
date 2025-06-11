package pl.janksiegowy.backend.shared.numerator;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import pl.janksiegowy.backend.shared.MigrationService;

@AllArgsConstructor
public class NumeratorConfig {

    private final NumeratorRepository numeratorRepository;
    private final MigrationService migrationService;
    private final CounterRepository counterRepository;

    @Bean
    NumeratorFacade numeratorFacade() {
        return new NumeratorFacade(
                numeratorRepository,
                new NumeratorFactory(),
                migrationService,
                counterRepository);
    }
}
