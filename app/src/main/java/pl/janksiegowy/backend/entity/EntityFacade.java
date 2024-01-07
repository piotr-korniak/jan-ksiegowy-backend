package pl.janksiegowy.backend.entity;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.dto.EntityDto;

import java.util.Optional;

@AllArgsConstructor
public class EntityFacade {

    private final EntityFactory factory;
    private final EntityRepository repository;

    public Entity save( EntityDto source) {
        return repository.save( Optional.ofNullable( source.getEntityId())
                .map( entityId-> repository.findEntityByEntityIdAndDate( entityId, source.getDate())
                        .map( entity-> factory.update( source, entity))     // Update Entity history
                        .orElse( factory.update( source)))                  // New Entity history
                .orElse( factory.from( source))                             // New Entity
        );
    }
}
