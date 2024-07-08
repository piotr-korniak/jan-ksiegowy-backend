package pl.janksiegowy.backend.finances.charge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodFacade;

@Configuration
public class ChargeConfiguration {

    @Bean
    ChargeFacade chargeFacade( final EntityRepository entities,
                             final PeriodFacade period,
                             final ChargeRepository charge,
                             final DecreeFacade decree) {
        return new ChargeFacade( new ChargeFactory( entities, period), charge, decree);
    }

}
