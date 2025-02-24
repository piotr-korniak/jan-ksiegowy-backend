package pl.janksiegowy.backend.declaration;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( "C")
public class Declaration_CIT extends PayableDeclaration {
}
