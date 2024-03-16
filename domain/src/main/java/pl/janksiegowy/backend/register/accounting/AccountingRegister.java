package pl.janksiegowy.backend.register.accounting;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.register.Register;

@Getter
@Entity
@DiscriminatorValue( value= "A")
public class AccountingRegister extends Register {

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private AccountingRegisterType type;

    @Enumerated( EnumType.STRING)
    private AccountingRegisterType kind= AccountingRegisterType.A;

}

