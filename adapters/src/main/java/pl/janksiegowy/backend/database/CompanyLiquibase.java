package pl.janksiegowy.backend.database;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import pl.janksiegowy.backend.company.Company;
import pl.janksiegowy.backend.company.SqlCompanyRepository;
import pl.janksiegowy.backend.database.LiquibaseConfig;
import pl.janksiegowy.backend.tenant.SqlTenantRepository;
import pl.janksiegowy.backend.tenant.Tenant;
import pl.janksiegowy.backend.database.TenantContext.Context;

public class CompanyLiquibase implements InitializingBean {

    @Autowired
    private SqlTenantRepository tenants;

    @Autowired
    private SqlCompanyRepository companies;

    @Autowired
    @Qualifier( "companyLiquibaseProperties")
    private LiquibaseProperties companyLiquibaseProperties;

    @Autowired
    private TenantConnectionProvider tenantConnectionProvider;

    @Autowired
    @Qualifier( "webApplicationContext")
    private ResourceLoader resourceLoader;

    @Override
    public void afterPropertiesSet() throws Exception {
        SpringLiquibase springLiquibase= LiquibaseConfig
                .getSpringLiquibase( companyLiquibaseProperties);
        springLiquibase.setResourceLoader( resourceLoader);

        for( Tenant tenant: tenants.findAll()){
            TenantContext.setCurrentTenant(
                    Context.create().tenant( tenant.getCode()));

            for( Company company: companies.findAll()){
                springLiquibase.setDefaultSchema( company.getCode());
                springLiquibase.setDataSource(
                        tenantConnectionProvider.selectDataSource( tenant.getCode()));
                springLiquibase.afterPropertiesSet();
            }
            TenantContext.clear();
        }
    }
}
