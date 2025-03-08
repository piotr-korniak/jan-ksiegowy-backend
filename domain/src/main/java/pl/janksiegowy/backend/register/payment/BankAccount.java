package pl.janksiegowy.backend.register.payment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.register.RegisterType;
import pl.janksiegowy.backend.shared.financial.Currency;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@DiscriminatorValue( value= "B")
@SecondaryTable( name= BankAccount.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public class BankAccount extends PaymentRegister {
    static final String TABLE_NAME= "BANK_ACCOUNTS";

    @Column( table= TABLE_NAME, name = "NUMBER")
    private String bankNumber;

    @Column( table= TABLE_NAME)
    @Enumerated( EnumType.STRING )
    private Currency currency;

    @Override public RegisterType getType() {
        return RegisterType.B;
    }
}
