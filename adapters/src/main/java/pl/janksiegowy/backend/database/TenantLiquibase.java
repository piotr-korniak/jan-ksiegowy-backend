package pl.janksiegowy.backend.database;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import pl.janksiegowy.backend.database.LiquibaseConfig;
import pl.janksiegowy.backend.database.TenantConnectionProvider;
import pl.janksiegowy.backend.tenant.SqlTenantRepository;
import pl.janksiegowy.backend.tenant.Tenant;

public class TenantLiquibase implements InitializingBean {

    @Autowired
    private SqlTenantRepository tenants;

    @Autowired
    @Qualifier( "tenantLiquibaseProperties")
    private LiquibaseProperties tenantLiquibaseProperties;

    @Autowired
    private TenantConnectionProvider tenantConnectionProvider;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void afterPropertiesSet() throws Exception {
        SpringLiquibase springLiquibase= LiquibaseConfig
                .getSpringLiquibase( tenantLiquibaseProperties);
        springLiquibase.setResourceLoader( resourceLoader);

        for( Tenant scene: tenants.findAll()){
            TenantContext.setCurrentTenant( scene.getCode());

            springLiquibase.setDataSource( tenantConnectionProvider
                    .selectDataSource( scene.getCode()));
            springLiquibase.afterPropertiesSet();

            TenantContext.clear();
        }
    }
}
