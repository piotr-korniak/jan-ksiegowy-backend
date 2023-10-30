package pl.janksiegowy.backend.entity;

import pl.janksiegowy.backend.entity.dto.EntityDto;

import java.util.Optional;

public interface EntityQueryRepository {

    Optional<EntityDto> findByCountryAndTypeAndTaxNumber(
            Country country, EntityType type, String taxNumber);
}
