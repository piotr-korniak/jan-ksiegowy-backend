package pl.janksiegowy.backend.register.payment;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "C")
public class CashDesk extends PaymentRegister{
}
