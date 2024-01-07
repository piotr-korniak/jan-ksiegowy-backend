package pl.janksiegowy.backend.database;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.company.CompanyFacade;
import pl.janksiegowy.backend.company.CompanyInitializer;
import pl.janksiegowy.backend.entity.EntityFacade;
import pl.janksiegowy.backend.entity.EntityInitializer;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.invoice.*;
import pl.janksiegowy.backend.metric.MetricFactory;
import pl.janksiegowy.backend.metric.MetricInitializer;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.tenant.TenantFacade;
import pl.janksiegowy.backend.tenant.TenantInitializer;

@Component
@Order( 0)
public class DatabaseWarmUp implements ApplicationListener<ContextRefreshedEvent> {

    private final TenantInitializer tenants;
    private final TenantFacade tenant;

    private final CompanyInitializer companies;
    private final CompanyFacade company;

    private final MetricInitializer metrics;

    private final DataLoader loader;

    public DatabaseWarmUp(
            final TenantFacade tenant,
            final CompanyFacade company,
            final MetricRepository metric,
            final EntityQueryRepository entities,
            final EntityFacade entity,
            final InvoiceFacade invoice,
            final SettlementQueryRepository settlements,
            final DataLoader loader) {
        this.tenant= tenant;
        this.tenants= new TenantInitializer( loader, tenant);
        this.company= company;
        this.companies= new CompanyInitializer( company);
        this.metrics= new MetricInitializer( new MetricFactory(), metric, loader);
        this.loader= loader;


    }

    @Override
    public void onApplicationEvent( @NonNull ContextRefreshedEvent event) {

//        tenants.init();
/*
        TenantContext.setCurrentTenant( "eleftheria");
        companies.init();
        TenantContext.clear();

        TenantContext.setCurrentTenant( "eleftheria", "pl5862321911");

*/
//        metrics.init();
//        entities.init();
//        invoices.init();

//        TenantContext.clear();

    }
}
