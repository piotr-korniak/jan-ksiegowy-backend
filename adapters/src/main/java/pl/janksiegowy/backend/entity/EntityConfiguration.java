package pl.janksiegowy.backend.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityConfiguration {

    @Bean
    EntityFacade entityFacade( final EntityRepository entities) {
        return new EntityFacade( new EntityFactory(), entities);
    }
}
