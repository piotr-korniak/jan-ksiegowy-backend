package pl.janksiegowy.backend.register;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterFactory;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

@Configuration
public class RegisterConfiguration {

    @Bean
    InvoiceRegisterFactory registerFactory( final NumeratorFacade numerators) {
        return new InvoiceRegisterFactory( numerators);
    }
}
