package pl.janksiegowy.backend.entity;

import pl.janksiegowy.backend.entity.dto.EntityDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EntityQueryRepository {

    Optional<EntityDto> findByCountryAndTypeAndTaxNumber(
            Country country, EntityType type, String taxNumber);

    default Optional<EntityDto> findContactByCountryAndTaxNumber( Country country, String taxNumber) {
        return findByCountryAndTypeAndTaxNumber( country, EntityType.C, taxNumber);
    }

    Optional<EntityDto> findByTypeAndTaxNumber( EntityType type, String taxNumber);
    Optional<EntityDto> findByCountryAndTaxNumberAndDateAndTypeIn(
            Country country, String taxNumber, LocalDate date, EntityType... types);

    <T> List<T> findByType( Class<T> tClass, EntityType type);
}
