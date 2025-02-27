package pl.janksiegowy.backend.tax;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.declaration.StatementFacade;

import java.util.List;

@Configuration
public class taxConfiguration {


    @Bean
    TaxFacade taxFacade( final List<TaxBundle> taxBundles,
                         final StatementFacade statementFacade) {
        return new TaxFacade( taxBundles, statementFacade);
    }
}
