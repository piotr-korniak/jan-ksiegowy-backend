package pl.janksiegowy.backend.register.invoice;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import pl.janksiegowy.backend.register.Register;

@Getter
@Entity
@DiscriminatorValue( value = "S")
public class SalesRegister extends InvoiceRegister {


}
