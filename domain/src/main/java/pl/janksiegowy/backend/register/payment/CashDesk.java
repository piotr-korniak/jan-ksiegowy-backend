package pl.janksiegowy.backend.register.payment;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.janksiegowy.backend.register.RegisterType;

@Entity
@DiscriminatorValue( value= "D")
public class CashDesk extends PaymentRegister{

    @Override public RegisterType getType() {
        return RegisterType.D;
    }
}
