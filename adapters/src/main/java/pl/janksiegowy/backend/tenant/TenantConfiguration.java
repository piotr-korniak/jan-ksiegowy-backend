package pl.janksiegowy.backend.tenant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantConfiguration {

    @Bean
    TenantFacade tenantFacade( final TenantService tenant,
                               final TenantQueryRepository tenants) {
        return new TenantFacade( tenant, tenants);
    }
}
