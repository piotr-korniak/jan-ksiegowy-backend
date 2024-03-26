package pl.janksiegowy.backend.finances.settlement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodFacade;

@Configuration
public class SettlementConfiguration {

    @Bean
    SettlementFacade settlementFacade( final PeriodFacade period,
                                       final EntityRepository entities,
                                       final SettlementRepository settlements) {
        return new SettlementFacade( new SettlementFactory( period, entities), settlements);
    }
}
