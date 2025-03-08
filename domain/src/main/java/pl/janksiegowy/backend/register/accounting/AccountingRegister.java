package pl.janksiegowy.backend.register.accounting;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.register.Register;
import pl.janksiegowy.backend.register.RegisterType;

@Getter
@Entity
@DiscriminatorValue( value= "A")
public class AccountingRegister extends Register {

    @Override public RegisterType getType() {
        return RegisterType.A;
    }
}

