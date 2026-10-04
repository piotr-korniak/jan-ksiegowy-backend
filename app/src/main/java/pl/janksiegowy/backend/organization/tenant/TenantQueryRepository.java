package pl.janksiegowy.backend.organization.tenant;

import pl.janksiegowy.backend.organization.tenant.dto.TenantDto;

import java.util.Optional;

public interface TenantQueryRepository {

    Optional<TenantDto> findByCode( String code);
}
