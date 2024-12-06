package pl.janksiegowy.backend.metric;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.metric.dto.MetricDto;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.metric.dto.MetricDto.Proxy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@AllArgsConstructor
public class MetricInitializer {

    private final MetricFactory factory;
    private final MetricRepository repository;
    private final DataLoader loader;

    public void init() {
        /*
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
                            .vatPL( Boolean.valueOf( metric[12]))
                            .vatUE( Boolean.valueOf( metric[13]))
                            .rcCode( metric[14]))
                    );
                }
            });*/


        new CsvToBeanBuilder<MetricDto>( loader.getReader( "metrics.csv"))
                .withType( Proxy.class)
                .build()
                .parse()
                .stream()
                .filter(metricDto-> !repository.existById( metricDto.getId()))
                .map( factory::from)
                .forEach( repository::save);
    }
}
