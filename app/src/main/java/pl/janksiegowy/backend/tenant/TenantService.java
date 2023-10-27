package pl.janksiegowy.backend.tenant;

import pl.janksiegowy.backend.tenant.dto.TenantDto;

public interface TenantService {

    void create( String tenantCode, String password);

    void save( TenantDto source);
}
