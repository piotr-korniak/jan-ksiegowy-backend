package pl.janksiegowy.backend.period;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "A")
public class AnnualPeriod extends QuarterPeriod {

    @Override
    public <T> T accept( PeriodVisitor<T> visitor) {
        return visitor.visit( this);
    }
}
