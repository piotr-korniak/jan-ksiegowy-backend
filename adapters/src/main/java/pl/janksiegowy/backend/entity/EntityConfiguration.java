package pl.janksiegowy.backend.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

@Configuration
public class EntityConfiguration {
    @Bean
    EntityFacade entityFacade( final EntityRepository entities,
                                final NumeratorFacade numerator) {
        return new EntityFacade( new EntityFactory( numerator), entities);
    }
}
