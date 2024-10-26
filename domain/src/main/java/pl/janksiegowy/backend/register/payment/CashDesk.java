package pl.janksiegowy.backend.register.payment;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "D")
public class CashDesk extends PaymentRegister{
}
