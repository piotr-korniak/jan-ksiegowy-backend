package pl.janksiegowy.backend.register;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegisterConfiguration {

    @Bean
    RegisterFactory registerFactory( ) {
        return new RegisterFactory();
    }
}
