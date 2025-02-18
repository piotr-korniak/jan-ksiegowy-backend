package pl.janksiegowy.backend.declaration;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( "R")
public class RegisterStatement extends Declaration {
}
