package pl.janksiegowy.backend.finances.clearing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.finances.settlement.SettlementRepository;

@Configuration
public class ClearingConfiguration {

    @Bean
    ClearingFactory clearingFactory( final SettlementRepository settlements) {
        return new ClearingFactory( settlements);
    }
}
