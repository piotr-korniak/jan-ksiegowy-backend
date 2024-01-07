package pl.janksiegowy.backend.database;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component( "companyIdentifierResolver")
public class CompanyIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Override public @NonNull String resolveCurrentTenantIdentifier() {
        return Optional.ofNullable( CompanyContext.getCurrentCompany())
                .orElse( "BOOTSTRAP");
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
