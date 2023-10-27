package pl.janksiegowy.backend.database;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.tenant.TenantQueryRepository;
import pl.janksiegowy.backend.tenant.dto.TenantDto;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Component
public class TenantConnectionProvider
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private LoadingCache<String, HikariDataSource> sceneDataSources;
    private final DataSourceProperties dataSourceProperties;

    private final TenantQueryRepository tenants;

    @Qualifier( "mainDataSource")
    private final DataSource mainDataSource;

    @Value( "${databases.tenant.datasource.url-prefix}")
    private String urlPrefix;

    @Value( "${databases.tenant.cache.maximumSize:10}")
    private Long maximumSize;

    @Value( "${databases.tenant.cache.expireAfterAccess:10}")
    private Integer expireAfterAccess;

    public TenantConnectionProvider( @Qualifier( "mainDataSource")
                                     DataSource mainDataSource,
                                     @Qualifier( "mainDataSourceProperties")
                                     DataSourceProperties dataSourceProperties,
                                     TenantQueryRepository tenants) {
        this.mainDataSource= mainDataSource;
        this.dataSourceProperties= dataSourceProperties;
        this.tenants= tenants;
    }

    @PostConstruct
    private void createCache() {

        sceneDataSources= Caffeine.newBuilder()
                .maximumSize( maximumSize)
                .expireAfterAccess( expireAfterAccess, TimeUnit.MINUTES)
                .removalListener( (String key, HikariDataSource dataSource, RemovalCause cause)->
                        dataSource.close())
                .build( key-> tenants.findByCode( key)
                                .map( tenantDto-> createAndConfigureDataSource( tenantDto))
                                .orElseThrow( ()-> new RuntimeException( "No such tenant: " + key))
                    );

    }

    private HikariDataSource createAndConfigureDataSource( TenantDto tenantDto){
        var dataSource= dataSourceProperties
                .initializeDataSourceBuilder()
                .type( HikariDataSource.class)
                .url( urlPrefix+ tenantDto.getCode())
                .build();
        dataSource.setUsername( tenantDto.getCode());
        dataSource.setPassword( tenantDto.getPassword());
        return dataSource;
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return mainDataSource;
    }

    @Override
    public DataSource selectDataSource(String tenantIdentifier) {
        return sceneDataSources.get( tenantIdentifier);
    }
/*
    @Override
    public void releaseAnyConnection( Connection connection) throws SQLException {
        mainDataSource.getConnection().close();
    }*/
/*
    @Override
    public void releaseConnection( String tenantIdentifier, Connection connection) throws SQLException {
        sceneDataSources.invalidate( tenantIdentifier);
    }*/
}
