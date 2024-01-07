package pl.janksiegowy.backend.tenant;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.tenant.dto.TenantDto;

@AllArgsConstructor
public class TenantInitializer {

    private final DataLoader loader;
    private final TenantFacade tenant;

    public void init() {

        tenant.create( TenantDto.create()
                .code( "Eleftheria")
                .name( "Grupa Eleftheria")
                .password( "Sylwi@1970"));
    }
}
