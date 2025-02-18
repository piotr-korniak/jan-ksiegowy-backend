package pl.janksiegowy.backend.declaration;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( "D")
public class Declaration_DRA extends PayableDeclaration {
}
