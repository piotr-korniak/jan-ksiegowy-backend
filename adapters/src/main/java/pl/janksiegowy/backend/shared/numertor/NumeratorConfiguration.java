package pl.janksiegowy.backend.shared.numertor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.shared.numerator.CounterRepository;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.shared.numerator.NumeratorFactory;
import pl.janksiegowy.backend.shared.numerator.NumeratorRepository;

@Configuration
public class NumeratorConfiguration {

    @Bean
    NumeratorFacade numeratorFacade( final NumeratorRepository numerators,
                                     final CounterRepository counters) {
        return new NumeratorFacade( numerators, new NumeratorFactory(), counters);
    }
}
