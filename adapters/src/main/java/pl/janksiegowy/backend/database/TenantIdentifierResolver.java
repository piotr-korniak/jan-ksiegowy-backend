package pl.janksiegowy.backend.database;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component( "tenantIdentifierResolver")
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Override public String resolveCurrentTenantIdentifier() {
        return Optional.ofNullable( TenantContext.getCurrentTenant())
                .orElse( "BOOTSTRAP");
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
