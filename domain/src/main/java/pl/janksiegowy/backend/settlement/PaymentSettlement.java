package pl.janksiegowy.backend.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.payment.Payment;

@Entity
@DiscriminatorValue( "P")
public class PaymentSettlement extends Settlement {

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name= "DOCUMENT_ID")   //, referencedColumnName= "ID")
    @MapsId
    protected Payment payment;

    public void setPayment( Payment payment) {
        this.payment= payment;
        this.id= payment.getPaymentId();
    }

    @Override public <T> T accept( SettlementVisitor<T> visitor ) {
        return visitor.visit( this);
    }
}
