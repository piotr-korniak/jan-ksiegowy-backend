package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.payment.PaymentOld;

import java.math.BigDecimal;
import java.util.List;

@Getter

@Entity
@DiscriminatorValue( "P")
public class PaymentSettlement extends Settlement {

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name= "DOCUMENT_ID")   //, referencedColumnName= "ID")
    @MapsId
    protected PaymentOld payment;

    public void setPayment( PaymentOld payment) {
        this.payment= payment;
        this.settlementId = payment.getPaymentId();
    }

    @Override public <T> T accept( SettlementVisitor<T> visitor ) {
        return visitor.visit( this);
    }

    @Override
    public BigDecimal getAmount() {
        return null;
    }

    @Override
    public Settlement setAmount( BigDecimal amount ) {
        return null;
    }

    @Override
    public Settlement setClearings( List<Clearing> clearings ) {
        return null;
    }
}
