package pl.janksiegowy.backend.register.payment;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "B")
public class BankAccount extends PaymentRegister{
}
