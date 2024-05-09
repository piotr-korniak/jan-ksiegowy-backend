package pl.janksiegowy.backend.finances.note;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue( "I")
public class IssuedNote extends Note {

    @Enumerated( EnumType.STRING)
    private SettlementKind kind= SettlementKind.D;

    @Override
    public Document setAmount( BigDecimal amount) {
        this.dt= amount;
        return null;
    }

    @Override public BigDecimal getAmount() {
        return this.dt;
    }


}
