package pl.janksiegowy.backend.subdomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pl.janksiegowy.backend.tenant.TenantQueryRepository;

import java.util.Arrays;
import java.util.Optional;

public class SubdomainRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Autowired
    private TenantQueryRepository tenants;

    @Override
    protected RequestCondition<?> getCustomTypeCondition( Class<?> handlerType) {

        Optional.ofNullable( AnnotationUtils.findAnnotation( handlerType, SubdomainController.class))
                .map( subdomainController-> {
                    return new SubdomainRequestCondition( Arrays.asList( subdomainController.value()));
                });

        return Optional.ofNullable( AnnotationUtils.findAnnotation( handlerType, TenantController.class))
                .map( tenantController-> new TenantRequestCondition( tenants))
                .orElse( null);
    }

}
