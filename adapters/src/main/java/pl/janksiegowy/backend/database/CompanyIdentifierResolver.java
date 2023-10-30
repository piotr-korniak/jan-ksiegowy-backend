package pl.janksiegowy.backend.database;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component( "companyIdentifierResolver")
public class CompanyIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Override public String resolveCurrentTenantIdentifier() {
        return Optional.ofNullable( CompanyContext.getCurrentCompany())
                .orElse( "BOOTSTRAP");
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
