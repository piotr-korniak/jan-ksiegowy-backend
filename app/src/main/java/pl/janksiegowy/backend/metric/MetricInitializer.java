package pl.janksiegowy.backend.metric;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.metric.dto.MetricDto;
import pl.janksiegowy.backend.shared.DataLoader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@AllArgsConstructor
public class MetricInitializer {

    private final MetricFactory factory;
    private final MetricRepository repository;
    private final DataLoader loader;

    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "dd.MM.yyyy");

    public void init() {
        loader.readData( "metrics.txt")
            .forEach( metric-> {
                var id= LocalDate.parse( metric[0], formatter);
                if( !repository.existById( id)) {
                    repository.save(
                        factory.from(MetricDto.create()
                            .id( id)
                            .taxNumber( metric[1])
                            .registrationNumber( metric[2])
                            .businessNumber( metric[3])
                            .name( metric[4])
                            .address( metric[5])
                            .postcode( metric[6])
                            .town( metric[7])
                            .country( metric[8])
                            .capital( parse(metric[9], 2))
                            .vatQuarterly( Boolean.valueOf(metric[10]))
                            .citQuarterly( Boolean.valueOf(metric[11]))
                            .vatUE( Boolean.valueOf(metric[12]))
                            .rcCode( metric[13]))
                    );
                }
            });
    }

    private BigDecimal parse( String kwota, int precyzja){

        StringBuilder sb= new StringBuilder( kwota);
        // dostawiamy zera po przecinku, precyzja dla BigDecimal
        int n= precyzja;
        while( n--> 0)
            sb.append( '0');
        // zamieniamy przecinek na kropke
        n= kwota.lastIndexOf( ',');

        if( n>0){
            sb.replace( n, n+1, ".");
            sb.delete( n+precyzja+1, 100);
        }
        // usuwamy białe znaki
        while((n=sb.indexOf( "\u00A0"))> 0){
            sb.delete( n, n+1);
        }

        return new BigDecimal( sb.toString());
    }
}
