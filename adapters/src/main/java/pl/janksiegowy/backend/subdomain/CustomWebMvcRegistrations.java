package pl.janksiegowy.backend.subdomain;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pl.janksiegowy.backend.tenant.TenantQueryRepository;

@Component
@AllArgsConstructor
public class CustomWebMvcRegistrations implements WebMvcRegistrations {

    private final TenantQueryRepository tenants;

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping= new DomainRequestMappingHandlerMapping( tenants);
        handlerMapping.setOrder( 0);
        return handlerMapping;
    }
}
