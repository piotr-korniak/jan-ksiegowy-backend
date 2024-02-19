package pl.janksiegowy.backend.register;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegisterConfiguration {

    @Bean
    InvoiceRegisterFactory registerFactory( ) {
        return new InvoiceRegisterFactory();
    }
}
