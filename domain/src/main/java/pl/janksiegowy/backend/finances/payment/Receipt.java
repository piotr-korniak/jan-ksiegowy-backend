package pl.janksiegowy.backend.finances.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import pl.janksiegowy.backend.finances.document.Document;

import java.math.BigDecimal;

@Entity
@Table( name= "PAYMENTS")
@PrimaryKeyJoinColumn( name= "ID")

@DiscriminatorValue( "R")
public class Receipt extends PaymentDocument {
    @Override public Document setAmount( BigDecimal amount) {
        this.Dt= amount;
        this.Ct= BigDecimal.ZERO;
        return this;
    }
}
