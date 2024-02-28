package pl.janksiegowy.backend.register.invoice;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
@DiscriminatorValue( value= "P")
public class PurchaseRegister extends InvoiceRegister {}
