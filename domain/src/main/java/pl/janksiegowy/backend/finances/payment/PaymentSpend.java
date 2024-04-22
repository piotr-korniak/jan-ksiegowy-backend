package pl.janksiegowy.backend.finances.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.janksiegowy.backend.finances.clearing.Clearing;

import java.util.List;

@Entity
@DiscriminatorValue( "S")
public class PaymentSpend extends PaymentOld {

    @Override public <T> T accept( PaymentVisitor<T> visitor ) {
        return visitor.visit( this);
    }
}
