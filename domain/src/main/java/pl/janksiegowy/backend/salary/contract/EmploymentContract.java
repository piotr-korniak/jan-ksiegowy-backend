package pl.janksiegowy.backend.salary.contract;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "E")
public class EmploymentContract extends Contract {
}
