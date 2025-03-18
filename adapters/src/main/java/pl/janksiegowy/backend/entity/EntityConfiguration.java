package pl.janksiegowy.backend.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.shared.MigrationService;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

@Configuration
public class EntityConfiguration {
    @Bean
    EntityFacade entityFacade( final EntityRepository entityRepository,
                               final EntityQueryRepository entities,
                               final NumeratorFacade numerator,
                               final MigrationService migrationService) {
        return new EntityFacade( new EntityFactory( numerator), entityRepository, entities, migrationService);
    }
}
