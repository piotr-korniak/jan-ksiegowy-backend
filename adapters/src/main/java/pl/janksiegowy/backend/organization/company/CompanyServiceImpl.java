package pl.janksiegowy.backend.organization.company;

import liquibase.exception.LiquibaseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.database.LiquibaseConfig;
import pl.janksiegowy.backend.database.TenantConnectionProvider;
import pl.janksiegowy.backend.database.TenantContext;

@Service
@DependsOn( "tenantLiquibase")
@EnableConfigurationProperties( LiquibaseProperties.class)
public class CompanyServiceImpl implements CompanyService {

    private static final String VALID_SCHEMA_NAME_REGEXP= "[A-Za-z0-9_]*";

    private final SqlCompanyRepository companies;
    private final TenantConnectionProvider tenants;
    private final JdbcTemplate jdbc;

    private final LiquibaseProperties liquibaseProperties;
    private final ResourceLoader resourceLoader;

    public CompanyServiceImpl( final SqlCompanyRepository companies,
                               final TenantConnectionProvider tenants,
                               final JdbcTemplate jdbc,
                               @Qualifier( "companyLiquibaseProperties")
                               final LiquibaseProperties liquibaseProperties,
                               final ResourceLoader resourceLoader) {
        this.companies= companies;
        this.tenants= tenants;
        this.jdbc= jdbc;
        this.liquibaseProperties= liquibaseProperties;
        this.resourceLoader= resourceLoader;
    }

    @Override
    public void create( String companyCode) {
        if( !companyCode.matches( VALID_SCHEMA_NAME_REGEXP))
            throw new RuntimeException( "Invalid schema name: "+ companyCode);

        var dataSource= tenants.selectDataSource(
                TenantContext.getCurrentTenant().getTenant());

        System.err.println( "Tenant: "+ TenantContext.getCurrentTenant().getTenant());


        try {
            jdbc.setDataSource( dataSource);
            System.err.println( "Current user: "+ jdbc.queryForObject("select current_user", String.class));
            System.err.println( "Current database: "+ jdbc.queryForObject("select current_database()", String.class));
            jdbc.execute( (StatementCallback<Boolean>) stmt->
                    stmt.execute("CREATE SCHEMA " + companyCode));
        } catch( DataAccessException e) {
            throw new RuntimeException( "Error when creating schema: "+ companyCode, e);
        }

        try {
            var springLiquibase= LiquibaseConfig.getSpringLiquibase( liquibaseProperties);
            springLiquibase.setDataSource( dataSource);
            springLiquibase.setDefaultSchema( companyCode);
            springLiquibase.setResourceLoader( resourceLoader);
            springLiquibase.afterPropertiesSet();
        } catch( LiquibaseException e) {
            throw new RuntimeException( "Error when populating schema: "+ companyCode, e);
        }

    }

}
