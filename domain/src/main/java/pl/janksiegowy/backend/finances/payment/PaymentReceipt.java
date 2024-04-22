package pl.janksiegowy.backend.finances.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import pl.janksiegowy.backend.finances.clearing.Clearing;

import java.util.List;

@Getter

@Entity
@DiscriminatorValue( "R")
public class PaymentReceipt extends PaymentOld {


    @Override public <T> T accept( PaymentVisitor<T> visitor ) {
        return visitor.visit( this);
    }
}
