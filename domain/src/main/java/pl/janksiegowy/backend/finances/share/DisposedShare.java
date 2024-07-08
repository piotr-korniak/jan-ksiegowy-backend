package pl.janksiegowy.backend.finances.share;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue( "D")
public class DisposedShare extends Share {

    @Enumerated( EnumType.STRING)
    private SettlementKind kind= SettlementKind.D;

    @Override
    public Document setAmount( BigDecimal amount) {
        this.setDt( amount);
        return this;
    }

    @Override
    public BigDecimal getAmount() {
        return getDt();
    }
}
