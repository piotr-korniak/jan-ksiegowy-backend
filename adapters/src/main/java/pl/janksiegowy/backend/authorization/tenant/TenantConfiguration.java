package pl.janksiegowy.backend.authorization.tenant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.tenant.TenantFacade;
import pl.janksiegowy.backend.tenant.TenantQueryRepository;
import pl.janksiegowy.backend.tenant.TenantService;

@Configuration
public class TenantConfiguration {

    @Bean
    TenantFacade tenantFacade(final TenantService tenant,
                              final TenantQueryRepository tenants) {
        return new TenantFacade( tenant, tenants);
    }
}
