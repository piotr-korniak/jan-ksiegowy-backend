package pl.janksiegowy.backend.register;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterFactory;

@Configuration
public class RegisterConfiguration {

    @Bean
    InvoiceRegisterFactory registerFactory( ) {
        return new InvoiceRegisterFactory();
    }
}
