package pl.janksiegowy.backend.declaration;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( "V")
public class Declaration_VAT extends PayableDeclaration {
}
