package pl.janksiegowy.backend.entity;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface EntityRepository {
    Entity save( Entity entity);

    Optional<Entity> findEntityByEntityIdAndDate( UUID entityId, LocalDate date);
}
