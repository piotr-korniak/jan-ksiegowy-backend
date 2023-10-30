package pl.janksiegowy.backend.metric;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.metric.dto.MetricDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
public class MetricInitializer {

    private final MetricFactory factory;
    private final MetricRepository repository;

    public void init() {
        repository.save( factory.from(
                MetricDto.create()
                        .id( LocalDate.of( 2017, 8, 30))
                        .capital( BigDecimal.valueOf( 5000))
                        .name( "Eleutheria Usługi Informatyczne Sp. z o.o."))
        );
    }
}
