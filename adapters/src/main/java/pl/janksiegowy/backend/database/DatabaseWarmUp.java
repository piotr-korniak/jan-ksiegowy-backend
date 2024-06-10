package pl.janksiegowy.backend.database;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.company.CompanyFacade;
import pl.janksiegowy.backend.company.CompanyInitializer;
import pl.janksiegowy.backend.tenant.TenantFacade;
import pl.janksiegowy.backend.tenant.TenantInitializer;

@Component
@Order( 0)
public class DatabaseWarmUp implements ApplicationListener<ContextRefreshedEvent> {

    //private final TenantInitializer tenants;
    private final TenantFacade tenant;

    private final CompanyInitializer companies;
    private final CompanyFacade company;

    public DatabaseWarmUp( final TenantFacade tenant,
                           final CompanyFacade company){

        this.tenant= tenant;
        //this.tenants= new TenantInitializer( tenant);
        this.company= company;
        this.companies= new CompanyInitializer( company);
    }

    @Override
    public void onApplicationEvent( @NonNull ContextRefreshedEvent event) {

      //  tenants.init();

        TenantContext.setCurrentTenant( TenantContext.Context.create().tenant( "eleftheria"));  //.setCurrentTenant( "eleftheria");
        companies.init();
    }
}
