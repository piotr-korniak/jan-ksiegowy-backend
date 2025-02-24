package pl.janksiegowy.backend.declaration;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( "J")
public class Declaration_JPK extends RegisterStatement{
}
