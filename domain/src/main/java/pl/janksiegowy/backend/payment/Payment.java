package pl.janksiegowy.backend.payment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.settlement.InvoiceSettlement;
import pl.janksiegowy.backend.settlement.PaymentSettlement;
import pl.janksiegowy.backend.settlement.Settlement;
import pl.janksiegowy.backend.settlement.StatementSettlement;

import java.time.LocalDate;
import java.util.UUID;

@Getter

@Entity
@Table( name= "PAYMENTS")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public abstract class Payment {

    @Id
    @Column( name= "ID")
    private UUID paymentId;

    @OneToOne( mappedBy= "payment", cascade = CascadeType.ALL)
    protected PaymentSettlement settlement;

    public Payment setPaymentId( UUID paymentId) {
        this.paymentId= paymentId;
        settlement.setPayment( this);
        return this;
    }

    public String getNumber() {
        return settlement.getNumber();
    }
    public Payment setNumber( String number) {
        settlement.setNumber( number);
        return this;
    };

    public Payment setSettlement( PaymentSettlement settlement) {
        this.settlement= settlement;
        return this;
    }

    public abstract <T> T accept( PaymentVisitor<T> visitor);

    public Payment setDate( LocalDate date) {
        this.settlement.setDate( date);
        this.settlement.setDue( date);
        return this;
    }

    public interface PaymentVisitor<T> {
        T visit( PaymentReceipt payment);
        T visit( PaymentSpend payment);
    }
}
