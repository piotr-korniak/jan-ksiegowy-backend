package pl.janksiegowy.backend.database;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityFacade;
import pl.janksiegowy.backend.entity.EntityInitializer;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.metric.MetricFactory;
import pl.janksiegowy.backend.metric.MetricInitializer;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.tenant.TenantFacade;
import pl.janksiegowy.backend.tenant.TenantInitializer;
import pl.janksiegowy.backend.company.CompanyFacade;
import pl.janksiegowy.backend.company.CompanyInitializer;

@Component
@Order( 0)
public class DatabaseWarmUp implements ApplicationListener<ContextRefreshedEvent> {

    private final TenantInitializer tenants;
    private final TenantFacade tenant;

    private final CompanyInitializer companies;
    private final CompanyFacade company;

    private final MetricInitializer metrics;
    private final EntityInitializer entities;

    public DatabaseWarmUp(final ResourceLoader loader,
                          final TenantFacade tenant,
                          final CompanyFacade company,
                          final MetricRepository metric,
                          final EntityQueryRepository entities,
                          final EntityFacade entity) {
        this.tenant= tenant;
        this.tenants= new TenantInitializer( tenant);
        this.company= company;
        this.companies= new CompanyInitializer( company);
        this.metrics= new MetricInitializer( new MetricFactory(), metric);
        this.entities= new EntityInitializer( new DataLoader(loader), entities, entity);
    }

    @Override
    public void onApplicationEvent( @NonNull ContextRefreshedEvent event) {

        tenants.init();

        TenantContext.setCurrentTenant( "eleftheria");
        companies.init();

        CompanyContext.setCurrentCompany( "pl5862321911");
        metrics.init();
        entities.init();


        CompanyContext.clear();
        TenantContext.clear();

    }
}
