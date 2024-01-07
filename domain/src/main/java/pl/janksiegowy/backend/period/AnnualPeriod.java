package pl.janksiegowy.backend.period;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "A")
public class AnnualPeriod extends QuaterPeriod{
}
