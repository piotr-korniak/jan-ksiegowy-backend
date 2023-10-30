package pl.janksiegowy.backend.metric;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "METRICS")
public class Metric {

    @Id
    @Temporal( TemporalType.DATE)
    private LocalDate id;

    private BigDecimal capital;
    private String name;
/*
    @ManyToOne(fetch = FetchType.EAGER)
    private Revenue us;
*/
    //@Value( "false")
    private boolean vatQuarterly= false;

    //@Value( "fals")
    private boolean citQuarterly= false;

}
