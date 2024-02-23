package pl.janksiegowy.backend.database;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Locale;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages= { "pl.janksiegowy.backend.metric",
                        "pl.janksiegowy.backend.entity",
                        "pl.janksiegowy.backend.invoice",
                        "pl.janksiegowy.backend.finances",
                        "pl.janksiegowy.backend.register",
                        "pl.janksiegowy.backend.period",
                        "pl.janksiegowy.backend.item",
                        "pl.janksiegowy.backend.invoice_line",
                        "pl.janksiegowy.backend.statement",
                        "pl.janksiegowy.backend.shared"},
        entityManagerFactoryRef= "companyEntityManagerFactory",
        transactionManagerRef= "companyTransactionManager"
)
@RequiredArgsConstructor
@EnableConfigurationProperties( JpaProperties.class)
public class CompanyPersistenceConfig {

    private final ConfigurableListableBeanFactory beanFactory;
    private final JpaProperties jpaProperties;

    @Bean
    public JpaTransactionManager companyTransactionManager( @Qualifier( "companyEntityManagerFactory")
                                                            EntityManagerFactory emf) {
        return new JpaTransactionManager( emf);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean companyEntityManagerFactory(
            @Qualifier( "tenantConnectionProvider") MultiTenantConnectionProvider tenantProvider,
            @Qualifier( "tenantIdentifierResolver") CurrentTenantIdentifierResolver companyResolver) {

        LocalContainerEntityManagerFactoryBean emf= new LocalContainerEntityManagerFactoryBean();

        emf.setPersistenceUnitName( "company-persistence-unit");
        emf.setPackagesToScan( "pl.janksiegowy.backend.metric",
                               "pl.janksiegowy.backend.entity",
                               "pl.janksiegowy.backend.invoice",
                               "pl.janksiegowy.backend.finances",
                               "pl.janksiegowy.backend.register",
                               "pl.janksiegowy.backend.period",
                               "pl.janksiegowy.backend.item",
                                "pl.janksiegowy.backend.invoice_line",
                                "pl.janksiegowy.backend.statement",
                                "pl.janksiegowy.backend.shared");
        //emf.setPackagesToScan( entityPackages);

        JpaVendorAdapter vendorAdapter= new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter( vendorAdapter);

        emf.setJpaPropertyMap( jpaProperties.getProperties());
        emf.setJpaPropertyMap( Map.of(
                AvailableSettings.PHYSICAL_NAMING_STRATEGY,
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy",
                AvailableSettings.IMPLICIT_NAMING_STRATEGY,
                "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
                AvailableSettings.BEAN_CONTAINER, new SpringBeanContainer( this.beanFactory),
                AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, tenantProvider,
                AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, companyResolver));
        return emf;
    }
}
