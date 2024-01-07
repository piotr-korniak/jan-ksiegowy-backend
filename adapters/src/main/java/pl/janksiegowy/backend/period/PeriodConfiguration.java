package pl.janksiegowy.backend.period;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.metric.MetricRepository;

@Configuration
public class PeriodConfiguration {

    @Bean
    PeriodFacade periodFacade( final MetricRepository metrics,
                               final PeriodRepository periods) {
        return new PeriodFacade( new PeriodFactory( metrics), periods, metrics);
    }
}
