package pl.janksiegowy.backend.metric;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.metric.dto.MetricDto;

@AllArgsConstructor
public class MetricFactory {

    public Metric from( MetricDto source) {
        return new Metric()
                .setId( source.getId())
                .setCapital( source.getCapital())
                .setName( source.getName());
    }

}
