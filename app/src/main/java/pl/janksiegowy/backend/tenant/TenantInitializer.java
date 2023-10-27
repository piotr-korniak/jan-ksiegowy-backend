package pl.janksiegowy.backend.tenant;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.tenant.dto.TenantDto;

@AllArgsConstructor
public class TenantInitializer {

    private final TenantFacade tenant;
    public void init() {
        tenant.create( TenantDto.create()
                .code( "Eleftheria")
                .name( "Grupa Eleftheria")
                .password( "Sylwi@1970"));
    }
}
