package pl.janksiegowy.backend.tenant;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.tenant.dto.TenantDto;

@AllArgsConstructor
public class TenantFacade {

    private final TenantService service;
    private final TenantQueryRepository tenants;

    public void create( TenantDto source) {

        if( tenants.existsByCode( source.getCode()))
            return;

        try {
            service.create( source.getCode(), source.getPassword());
            service.save( source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
