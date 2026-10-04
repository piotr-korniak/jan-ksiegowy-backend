package pl.janksiegowy.backend.organization.tenant;

import java.util.Optional;

public interface TenantRepository {
    Tenant save( Tenant tenant);
    Optional<Tenant> findByCode( String code);
}
