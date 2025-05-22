package pl.janksiegowy.backend.database;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages= { "pl.janksiegowy.backend.authorization.tenant",
                        "pl.janksiegowy.backend.authorization.user"},
        entityManagerFactoryRef= "mainEntityManagerFactory",
        transactionManagerRef= "mainTransactionManager"
)
@RequiredArgsConstructor
@EnableConfigurationProperties( JpaProperties.class)
public class MainPersistenceConfig {

    private final ConfigurableListableBeanFactory beanFactory;
    private final JpaProperties jpaProperties;

    //@Value( "${databases.main.packages}")
    //private String[] entityPackages;

    @Bean
    public JpaTransactionManager mainTransactionManager( @Qualifier( "mainEntityManagerFactory")
                                                         EntityManagerFactory emf) {
        return new JpaTransactionManager( emf);
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory( @Qualifier( "mainDataSource")
                                                                            DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf= new LocalContainerEntityManagerFactoryBean();

        emf.setPersistenceUnitName( "main-persistence-unit");
        //emf.setPackagesToScan( entityPackages); // entityManger packages
        emf.setPackagesToScan( "pl.janksiegowy.backend.authorization.tenant",
                                "pl.janksiegowy.backend.authorization.user");
        emf.setDataSource( dataSource);

        emf.setJpaVendorAdapter( new HibernateJpaVendorAdapter());

        emf.setJpaPropertyMap( jpaProperties.getProperties());
        emf.setJpaPropertyMap( Map.of(
                AvailableSettings.PHYSICAL_NAMING_STRATEGY,
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy",
                AvailableSettings.IMPLICIT_NAMING_STRATEGY,
                "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
                AvailableSettings.BEAN_CONTAINER,
                new SpringBeanContainer( this.beanFactory)
        ));
        return emf;
    }
}
