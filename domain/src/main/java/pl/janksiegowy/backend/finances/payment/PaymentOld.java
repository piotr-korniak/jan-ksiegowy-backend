package pl.janksiegowy.backend.finances.payment;


import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DiscriminatorOptions;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.register.payment.PaymentRegister;
import pl.janksiegowy.backend.finances.settlement.PaymentSettlement;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter

@Entity
@Table( name= "PAYMENTS")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
@DiscriminatorOptions( force= true)
public abstract class PaymentOld {

    @Id
    @Column( name= "ID")
    private UUID paymentId;

    @OneToOne( mappedBy= "payment", cascade = CascadeType.ALL)
    protected PaymentSettlement settlement;

    @ManyToOne
    private PaymentRegister register;

    @Enumerated( EnumType.STRING)
    @Column( insertable= false, updatable= false)
    private PaymentType type;

    public PaymentType getType() {
        return PaymentType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }


     public LocalDate getDate() {
        return settlement.getDate();
    }


    public PaymentOld setPaymentId( UUID paymentId) {
        this.paymentId= paymentId;
        settlement.setPayment( this);
        return this;
    }

    public String getNumber() {
        return settlement.getNumber();
    }

    public PaymentOld setNumber( String number) {
        settlement.setNumber( number);
        return this;
    };

    public PaymentOld setSettlement( PaymentSettlement settlement) {
        this.settlement= settlement;
        return this;
    }

    public abstract <T> T accept( PaymentVisitor<T> visitor);

    public PaymentOld setDate( LocalDate date) {
        this.settlement.setDate( date);
        this.settlement.setDue( date);
        return this;
    }

    public PaymentOld setRegister( PaymentRegister register ) {
        this.register= register;
        return this;
    }

    public PaymentOld setPeriod( MonthPeriod period ) {
        this.settlement.setPeriod( period);
        return this;
    }

    public PaymentOld setEntity( pl.janksiegowy.backend.entity.Entity entity) {
        settlement.setEntity( entity);
        return this;
    };


    public interface PaymentVisitor<T> {
        T visit( PaymentReceipt payment);
        T visit( PaymentSpend payment);
    }
}
