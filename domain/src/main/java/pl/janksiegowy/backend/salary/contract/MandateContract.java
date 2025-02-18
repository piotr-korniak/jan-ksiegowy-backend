package pl.janksiegowy.backend.salary.contract;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "M")
public class MandateContract extends Contract {
}
