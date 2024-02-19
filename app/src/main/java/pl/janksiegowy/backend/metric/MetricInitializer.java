package pl.janksiegowy.backend.metric;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.metric.dto.MetricDto;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;

@AllArgsConstructor
public class MetricInitializer {

    private final MetricFactory factory;
    private final MetricRepository repository;
    private final DataLoader loader;

    public void init() {
        loader.readData( "metrics.txt")
            .forEach( metric-> {
                var id= Util.toLocalDate( metric[0]);
                if( !repository.existById( id)) {
                    repository.save(
                        factory.from( MetricDto.create()
                            .id( id)
                            .taxNumber( metric[1])
                            .registrationNumber( metric[2])
                            .businessNumber( metric[3])
                            .name( metric[4])
                            .address( metric[5])
                            .postcode( metric[6])
                            .town( metric[7])
                            .country( metric[8])
                            .capital( Util.toBigDecimal( metric[9], 2))
                            .vatQuarterly( Boolean.valueOf (metric[10]))
                            .citQuarterly( Boolean.valueOf (metric[11]))
                            .vatUE( Boolean.valueOf( metric[12]))
                            .rcCode( metric[13]))
                    );
                }
            });
    }
}
