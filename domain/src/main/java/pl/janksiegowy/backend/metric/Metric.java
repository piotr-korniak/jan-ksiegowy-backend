package pl.janksiegowy.backend.metric;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.period.tax.CIT;
import pl.janksiegowy.backend.period.tax.VAT;

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

    private String taxNumber;
    private String registrationNumber;
    private String businessNumber;
    private String name;

    private String address;
    private String town;
    private String postcode;
    private String country;

    private BigDecimal capital;

    private boolean vatQuarterly= false;
    private boolean citQuarterly= false;

    @Column( name = "vat_pl")
    private boolean vatPL= false;
    @Column( name = "vat_ue")
    private boolean vatUE= false;

    private String rcCode;

    public CIT isCitMonthly() {
        return citQuarterly? CIT.No: CIT.Yes;
    }
    public CIT isCitQuarterly() {
        return citQuarterly? CIT.Yes: CIT.No;
    }

    public VAT isVatMonthly() {
        return vatPL&&!vatQuarterly? VAT.Yes: VAT.No;
    }
    public boolean isVatQuarterly() {
        return vatPL&&vatQuarterly;
    }

    public boolean isTaxQuarterly() {
        return citQuarterly||vatQuarterly;
    }

}
