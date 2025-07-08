package pl.janksiegowy.backend.subdomain;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import pl.janksiegowy.backend.database.TenantContext;
import pl.janksiegowy.backend.tenant.TenantQueryRepository;

@AllArgsConstructor
public class TenantRequestCondition implements RequestCondition<TenantRequestCondition> {

    private final TenantQueryRepository tenants;


    @Override
    public TenantRequestCondition getMatchingCondition( HttpServletRequest request) {

        return tenants.findByCode( request.getServerName().replaceAll("\\..*", ""))
                .map( tenantDto -> {
                    TenantContext.setCurrentTenant( TenantContext.Context.create()
                            .tenant( tenantDto.getCode())
                            .company( "pl5862321911"));
                    return this;
                }).orElse( null);
    }

    @Override
    public TenantRequestCondition combine( TenantRequestCondition other) {
        return this;
    }

    @Override
    public int compareTo( TenantRequestCondition other, HttpServletRequest request) {
        return 0;
    }
}
