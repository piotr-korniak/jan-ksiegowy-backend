package pl.janksiegowy.backend.authorization.tenant;

import java.util.Optional;

public interface TenantRepository {
    Optional<Tenant> findByCode( String code);
}
