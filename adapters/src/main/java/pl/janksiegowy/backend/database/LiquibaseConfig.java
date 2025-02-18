package pl.janksiegowy.backend.database;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Log4j2

@Configuration
@ConditionalOnProperty(
        name= "databases.main.liquibase.enabled",
        havingValue= "true",
        matchIfMissing= true)
@EnableConfigurationProperties( LiquibaseProperties.class)
public class LiquibaseConfig {

    @Bean
    @ConfigurationProperties( "databases.main.liquibase")
    public LiquibaseProperties mainLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    @ConfigurationProperties( "databases.tenant.liquibase")
    public LiquibaseProperties tenantLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    @ConfigurationProperties( "databases.company.liquibase")
    public LiquibaseProperties companyLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase mainLiquibase( @LiquibaseDataSource final HikariDataSource mainDataSource) {
        System.err.println( "Main Liquibase...");
        mainDataSource.setConnectionInitSql( "CREATE SCHEMA IF NOT EXISTS " + mainDataSource.getSchema());
        SpringLiquibase springLiquibase= getSpringLiquibase(mainLiquibaseProperties());
        springLiquibase.setDataSource( mainDataSource);
        return springLiquibase;
    }

    @Bean
    @DependsOn( "mainLiquibase")
    public TenantLiquibase tenantLiquibase(){
        System.err.println( "Tenant Liquibase...");
        return new TenantLiquibase();
    }

    @Bean
    @DependsOn( "tenantLiquibase")
    public CompanyLiquibase companyLiquibase(){
        System.err.println( "Company Liquibase...");
        return new CompanyLiquibase();
    }

    public static SpringLiquibase getSpringLiquibase( LiquibaseProperties liquibaseProperties) {
        SpringLiquibase liquibase= new SpringLiquibase();

        liquibase.setChangeLog( liquibaseProperties.getChangeLog());
        //liquibase.setContexts( liquibaseProperties.getContexts());
        liquibase.setDefaultSchema( liquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema( liquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace( liquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable( liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable( liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst( liquibaseProperties.isDropFirst());
        liquibase.setShouldRun( liquibaseProperties.isEnabled());
        //liquibase.setLabels( liquibaseProperties.getLabels());
        //liquibase.setLabelFilter( liquibaseProperties.getLabelFilter());
        liquibase.setChangeLogParameters( liquibaseProperties.getParameters());
        liquibase.setRollbackFile( liquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate( liquibaseProperties.isTestRollbackOnUpdate());
        return liquibase;
    }
}

