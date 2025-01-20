package pl.janksiegowy.backend.metric;

import org.checkerframework.checker.units.qual.C;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.shared.MigrationService;

@Configuration
public class MetricConfiguration {

    @Bean
    public MetricFacade metricFacade( final MetricRepository metricRepository,
                                      final MigrationService migrationService) {
        return new MetricFacade( new MetricFactory(), metricRepository, migrationService);
    }
}
