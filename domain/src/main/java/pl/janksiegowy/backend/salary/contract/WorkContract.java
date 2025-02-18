package pl.janksiegowy.backend.salary.contract;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "W")
public class WorkContract extends Contract {
}
