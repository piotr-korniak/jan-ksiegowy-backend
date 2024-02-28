package pl.janksiegowy.backend.register.accounting;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.register.Register;

@Getter
@Entity
public abstract class AccountingRegister extends Register {

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private AccountingRegisterType type;

    @Enumerated( EnumType.STRING)
    private AccountingRegisterKind kind= AccountingRegisterKind.A;

}

@Getter
@Entity
@DiscriminatorValue( value= "I")
class SalesAccountingRegister extends AccountingRegister {
}

@Getter
@Entity
@DiscriminatorValue( value= "Y")
class PaymentAccountingRegister extends AccountingRegister {
}
