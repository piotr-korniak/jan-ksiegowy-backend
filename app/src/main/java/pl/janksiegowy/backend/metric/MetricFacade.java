package pl.janksiegowy.backend.metric;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.metric.dto.MetricDto;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.metric.dto.MetricDto.Proxy;
import pl.janksiegowy.backend.shared.MigrationService;

@Log4j2

@AllArgsConstructor
public class MetricFacade {

    private final MetricFactory factory;
    private final MetricRepository repository;
    private final MigrationService migrationService;

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadMetrics()
                .forEach( metricDto-> {
                    counters[0]++;

                    if( !repository.existById( metricDto.getId())) {
                        repository.save( factory.from( metricDto));
                        counters[1]++;
                    }
                });

        log.warn( "Metrics migration complete!");
        return String.format( "%-40s %16s", "Metrics migration complete, added: ", counters[1]+ "/"+ counters[0]);
    }

}
