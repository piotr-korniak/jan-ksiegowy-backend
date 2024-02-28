package pl.janksiegowy.backend.register.invoice;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
@DiscriminatorValue( value= "S")
public class SalesRegister extends InvoiceRegister {}
