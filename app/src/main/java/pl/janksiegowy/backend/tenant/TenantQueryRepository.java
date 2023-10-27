package pl.janksiegowy.backend.tenant;

import pl.janksiegowy.backend.tenant.dto.TenantDto;

import java.util.Optional;

public interface TenantQueryRepository {
    boolean existsByCode( String code);
    Optional<TenantDto> findByCode( String code);
}
