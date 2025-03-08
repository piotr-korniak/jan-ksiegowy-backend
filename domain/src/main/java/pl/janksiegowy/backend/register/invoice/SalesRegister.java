package pl.janksiegowy.backend.register.invoice;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import pl.janksiegowy.backend.register.RegisterType;

@Getter
@Entity
@DiscriminatorValue( value= "S")
public class SalesRegister extends InvoiceRegister {

    @Override public RegisterType getType() {
        return RegisterType.S;
    }
}
