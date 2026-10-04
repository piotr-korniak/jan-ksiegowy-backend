package pl.janksiegowy.backend.organization.tenant;

import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.database.TenantContext;
import pl.janksiegowy.backend.organization.port.TenantContextPort;

import java.util.function.Supplier;

@Component
public class TenantContextAdapter implements TenantContextPort {

    @Override
    public void runInTenant( String tenant, Runnable action) {
        try {
            TenantContext.setCurrentTenant( TenantContext.Context.create().tenant( tenant));
            action.run();
        } finally {
            TenantContext.clear();
        }
    }

    @Override
    public <T> T runInTenant( String tenant, Supplier<T> action) {
        try {
            TenantContext.setCurrentTenant(TenantContext.Context.create().tenant( tenant));
            return action.get();
        } finally {
            TenantContext.clear();
        }
    }
}

