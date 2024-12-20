package pl.janksiegowy.backend.shared.loader;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import pl.janksiegowy.backend.shared.DataLoader;

@Log4j2
@Configuration
public class DataLoaderConfiguration  {

    @Bean
    @Profile( "local")
    public ResourceLoaderFake localTenantResourceLoader(
            @Qualifier( "webApplicationContext") ResourceLoader delegate,
            @Value( "${dataLoader.files.path}") String path) {
        return new LocalTenantResourceLoader( delegate, path);
    }

    @Bean
    @Profile( "azure")
    public ResourceLoaderFake azureTenantResourceLoader() {
        return new AzureTenantResourceLoader();
    }

    @Bean
    @Profile( "aws")
    public ResourceLoaderFake awsTenantResourceLoader( @Value( "${s3.bucked_name}") String buckedName) {
        return new AwsTenantResourceLoader( buckedName);
    }

    @Bean
    public DataLoader dataLoader( ResourceLoaderFake loader) {
        return new DataLoaderImpl( loader);
    }
}
