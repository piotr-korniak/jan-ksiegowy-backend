package pl.janksiegowy.backend.tenant;

import jakarta.annotation.PostConstruct;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.database.LiquibaseConfig;
import pl.janksiegowy.backend.tenant.dto.TenantDto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
@DependsOn( "mainLiquibase")
@EnableConfigurationProperties( LiquibaseProperties.class)
public class TenantServiceImpl implements TenantService {
    private static final String VALID_DATABASE_NAME_REGEXP= "[A-Za-z0-9_]*";

    private final SqlTenantRepository tenants;
    private final JdbcTemplate jdbc;
    private final LiquibaseProperties liquibaseProperties;
    private final ResourceLoader resourceLoader;

    @Value("${databases.main.datasource.username}")
    private String username;

    @Value("${databases.main.datasource.password}")
    private String password;

    @Value("${databases.tenant.datasource.url-prefix}")
    private String urlPrefix;

    public TenantServiceImpl( final SqlTenantRepository tenants,
                              final JdbcTemplate jdbc,
                              @Qualifier( "tenantLiquibaseProperties")
                              final LiquibaseProperties liquibaseProperties,
                              final ResourceLoader resourceLoader) {
        this.tenants= tenants;
        this.jdbc= jdbc;
        this.liquibaseProperties= liquibaseProperties;
        this.resourceLoader= resourceLoader;
    }

    private void runLiquibase( DataSource dataSource) throws LiquibaseException {
        SpringLiquibase springLiquibase= LiquibaseConfig.getSpringLiquibase( liquibaseProperties);
        springLiquibase.setDataSource( dataSource);
        springLiquibase.setResourceLoader( resourceLoader);
        springLiquibase.afterPropertiesSet();
    }

    @Override
    public void create( String tenantCode, String password) {

        if (!tenantCode.matches( VALID_DATABASE_NAME_REGEXP))
            throw new RuntimeException( "Invalid db name: " + tenantCode);

        try {
            createDatabase( tenantCode, password);
        } catch( DataAccessException e) {
            System.err.println( "Próbowano utworzyć bazę: "+ tenantCode);
            //throw new RuntimeException( "Error when creating db: "+ tenantCode, e);
        }

        try( Connection connection= DriverManager
                .getConnection(urlPrefix+ tenantCode, tenantCode, password)) {
            runLiquibase( new SingleConnectionDataSource( connection, false));
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException( "Error when populating db: "+ tenantCode, e);
        }
    }

    @Override
    public void save( TenantDto tenant) {
        tenants.save( from( tenant));
    }

    private Tenant from( TenantDto source) {
        return new Tenant()
                .setId( source.getId())
                .setName( source.getName())
                .setCode( source.getCode())
                .setPassword( source.getPassword());
    }

    private void createDatabase( String db, String password) {
        System.err.println( ">>> Create Database: "+ db);
        jdbc.execute( (StatementCallback<Boolean>) stmt->
                stmt.execute("CREATE DATABASE "+ db));
        jdbc.execute((StatementCallback<Boolean>) stmt->
                stmt.execute("CREATE USER "+ db+ " WITH ENCRYPTED PASSWORD '"+ password+ "'"));
        jdbc.execute((StatementCallback<Boolean>) stmt->
                stmt.execute("GRANT ALL PRIVILEGES ON DATABASE "+ db+ " TO "+ db));
    }
}
