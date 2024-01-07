package pl.janksiegowy.backend.period;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue( value= "Q")
public class QuaterPeriod extends Period {

    @ManyToOne( fetch= FetchType.EAGER)
    private AnnualPeriod parent;

    public Period setParent( AnnualPeriod parent) {
        this.parent= parent;
        return this;
    }
}
