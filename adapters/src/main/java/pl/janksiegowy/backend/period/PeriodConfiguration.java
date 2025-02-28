package pl.janksiegowy.backend.period;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.shared.MigrationService;

@Configuration
public class PeriodConfiguration {

    @Bean
    PeriodFacade periodFacade(final MetricRepository metrics,
                              final PeriodRepository periods,
                              final MigrationService migrationService) {
        return new PeriodFacade( new PeriodFactory( metrics), periods, metrics, migrationService);
    }
}
