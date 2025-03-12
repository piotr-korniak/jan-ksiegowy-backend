package pl.janksiegowy.backend.register.invoice;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import pl.janksiegowy.backend.register.RegisterType;

@Getter
@Entity
@DiscriminatorValue( value= "P")
public class PurchaseRegister extends InvoiceRegister {

    @Override public RegisterType getType() {
        return RegisterType.P;
    }

}
