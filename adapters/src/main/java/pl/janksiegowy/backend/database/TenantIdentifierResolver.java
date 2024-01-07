package pl.janksiegowy.backend.database;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.database.TenantContext.Context;

@Component( "tenantIdentifierResolver")
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<Context> {
    @Override public Context resolveCurrentTenantIdentifier() {
        return TenantContext.getCurrentTenant();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
