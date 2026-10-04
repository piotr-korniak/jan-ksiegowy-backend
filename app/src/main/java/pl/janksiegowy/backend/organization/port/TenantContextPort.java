package pl.janksiegowy.backend.organization.port;

import java.util.function.Supplier;

public interface TenantContextPort {
    <T> T runInTenant( String tenantId, Supplier<T> action);
    void runInTenant( String tenantId, Runnable action);
}
