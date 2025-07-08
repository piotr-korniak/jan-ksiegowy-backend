package pl.janksiegowy.backend.subdomain;

import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CompositeTypeRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pl.janksiegowy.backend.tenant.TenantQueryRepository;

@Component
@AllArgsConstructor
public class DomainRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final TenantQueryRepository tenants;

    @Override
    protected RequestCondition<?> getCustomTypeCondition( Class<?> handlerType) {
        if (!AnnotationUtils.isAnnotationDeclaredLocally( DomainController.class, handlerType)) {
            return null;
        }

        return new TenantRequestCondition( tenants);
    }

}
