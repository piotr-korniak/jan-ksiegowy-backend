package pl.janksiegowy.backend.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( "S")
public class PaymentSpend extends Payment{
    @Override public <T> T accept( PaymentVisitor<T> visitor ) {
        return visitor.visit( this);
    }
}
