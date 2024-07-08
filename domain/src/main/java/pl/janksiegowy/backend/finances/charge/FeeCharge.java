package pl.janksiegowy.backend.finances.charge;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue( "F")
public class FeeCharge extends Charge {

    @Enumerated( EnumType.STRING)
    private SettlementKind kind= SettlementKind.C;

    @Override
    public Document setAmount( BigDecimal amount) {
        setCt( amount);
        return this;
    }

    @Override
    public BigDecimal getAmount() {
        return getCt();
    }

    @Override
    public <T> T accept( DocumentVisitor<T> visitor ) {
        return visitor.visit( this);
    }
}
