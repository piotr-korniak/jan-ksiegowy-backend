package pl.janksiegowy.backend.entity;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.dto.EntityDto;

import java.util.Optional;

@AllArgsConstructor
public class EntityFacade {

    private final EntityFactory factory;
    private final EntityRepository repository;

    public void save( EntityDto source) {
        repository.save( Optional.ofNullable( source.getEntityId())
                .map( uuid-> repository.findEntityByEntityIdAndDate( uuid, source.getDate())
                        .map( item-> factory.update( item, source))     // Update Entity history
                        .orElse( factory.update( source)))              // New Entity history
                .orElse( factory.from( source))                         // New Entity
        );
    }
}
