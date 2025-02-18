package pl.janksiegowy.backend.entity;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface EntityRepository {
    Entity save( Entity entity);

    Optional<Entity> findByEntityIdAndDate( UUID entityId, LocalDate date);

    Optional<Entity> findEntityByEntityIdAndDate( UUID entityId, LocalDate date);

    Optional<Entity> findByCountryAndTaxNumberAndTypesAndDate(
            Country entityCountry, String entityTaxNumber, LocalDate date, EntityType... types);


}
