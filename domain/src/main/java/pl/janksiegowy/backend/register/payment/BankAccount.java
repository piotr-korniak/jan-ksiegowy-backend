package pl.janksiegowy.backend.register.payment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.financial.Currency;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@DiscriminatorValue( value= "A")
@SecondaryTable( name= BankAccount.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public class BankAccount extends PaymentRegister {
    static final String TABLE_NAME= "BANK_ACCOUNTS";

    @Column( table= TABLE_NAME)
    private String number;

    @Column( table= TABLE_NAME)
    @Enumerated( EnumType.STRING )
    private Currency currency;
}
