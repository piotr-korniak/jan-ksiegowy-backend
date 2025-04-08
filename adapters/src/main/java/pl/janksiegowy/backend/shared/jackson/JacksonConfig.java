package pl.janksiegowy.backend.shared.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule( new JavaTimeModule());
    }

    @Bean
    public CsvMapper csvMapper() {
        return (CsvMapper) new CsvMapper()
                .registerModule( new StringTrimModule())
                .registerModule( new JavaTimeModule());
    }

    @Bean
    public YAMLMapper jsonMapper() {
        return (YAMLMapper) new YAMLMapper()
                .registerModule( new JavaTimeModule());
    }

}
