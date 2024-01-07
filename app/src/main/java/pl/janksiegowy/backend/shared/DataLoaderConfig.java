package pl.janksiegowy.backend.shared;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Log4j2
@Configuration
public class DataLoaderConfig {
    @Bean
    DataLoader dataLoader( final ResourceLoader loader) {
        return new DataLoader( loader);
    }

    /*
    @Profile( "dev")
    @Qualifier( "loader")
    @Bean
    DataLoader localLoader() {
        return null;
    }

    @Profile( "prod")
    @Qualifier( "loader")
    @Bean
    DataLoader azureLoader() {
        return null;
    }*/
}
