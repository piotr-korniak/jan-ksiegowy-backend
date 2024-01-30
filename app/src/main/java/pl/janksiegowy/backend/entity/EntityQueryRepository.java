package pl.janksiegowy.backend.entity;

import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.invoice.dto.InvoiceViewDto;

import java.util.List;
import java.util.Optional;

public interface EntityQueryRepository {

    Optional<EntityDto> findByCountryAndTypeAndTaxNumber(
            Country country, EntityType type, String taxNumber);

    Optional<EntityDto> findByTypeAndTaxNumber( EntityType type, String taxNumber);

    <T> List<T> findByType( Class<T> tClass, EntityType type);
}
