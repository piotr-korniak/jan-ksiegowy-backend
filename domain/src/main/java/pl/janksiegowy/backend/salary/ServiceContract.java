package pl.janksiegowy.backend.salary;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "S")
public class ServiceContract extends Contract {
}
