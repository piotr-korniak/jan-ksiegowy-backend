package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.payment.Payment;

import java.util.List;

@Entity
@DiscriminatorValue( "P")
public class PaymentSettlement extends Settlement {

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name= "DOCUMENT_ID")   //, referencedColumnName= "ID")
    @MapsId
    protected Payment payment;

    public void setPayment( Payment payment) {
        this.payment= payment;
        this.settlementId = payment.getPaymentId();
    }

    @Override public <T> T accept( SettlementVisitor<T> visitor ) {
        return visitor.visit( this);
    }
}
