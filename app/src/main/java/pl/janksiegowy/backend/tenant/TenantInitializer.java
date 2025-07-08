package pl.janksiegowy.backend.tenant;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.tenant.dto.TenantDto;

@AllArgsConstructor
public class TenantInitializer {

    //private final DataLoader loader;
    private final TenantFacade tenant;

    public void init() {

        System.out.println( "Tenant initializer!");

        tenant.create( TenantDto.create()
                .code( "Eleftheria")
                .name( "Grupa Eleftheria")
                .password( "Sylwi@1970"));
        tenant.create( TenantDto.create()
                .code( "Test")
                .name( "Grupa Testowa")
                .password( "Sylwi@1970"));
    }
}
