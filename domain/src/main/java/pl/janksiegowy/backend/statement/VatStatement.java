package pl.janksiegowy.backend.statement;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( "V")
public class VatStatement extends Statement {
}
