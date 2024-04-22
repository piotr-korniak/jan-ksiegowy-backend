package pl.janksiegowy.backend.statement;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( "R")
public class JpkStatement extends Statement{
}
