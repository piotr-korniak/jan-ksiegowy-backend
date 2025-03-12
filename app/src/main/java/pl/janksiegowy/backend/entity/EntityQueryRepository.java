package pl.janksiegowy.backend.entity;

import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.invoice.dto.InvoiceViewDto;

import java.util.List;
import java.util.Optional;

public interface EntityQueryRepository {

    Optional<EntityDto> findByCountryAndTypeAndTaxNumber(
            Country country, EntityType type, String taxNumber);

    default Optional<EntityDto> findContactByCountryAndTaxNumber( Country country, String taxNumber) {
        return findByCountryAndTypeAndTaxNumber( country, EntityType.C, taxNumber);
    }

    Optional<EntityDto> findByTypeAndTaxNumber( EntityType type, String taxNumber);
    List<EntityDto> findByCountryAndTaxNumberAndTypes( Country country, String taxNumber, EntityType... types);

    <T> List<T> findByType( Class<T> tClass, EntityType type);
}
