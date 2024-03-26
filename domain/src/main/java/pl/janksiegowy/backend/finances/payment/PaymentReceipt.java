package pl.janksiegowy.backend.finances.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import pl.janksiegowy.backend.finances.clearing.Clearing;

import java.util.List;

@Entity
@DiscriminatorValue( "R")
public class PaymentReceipt extends Payment {

    @Override public <T> T accept( PaymentVisitor<T> visitor ) {
        return visitor.visit( this);
    }
}
