package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.document.Document;

import java.math.BigDecimal;
import java.util.List;

@Entity
@DiscriminatorValue( "F")
public class FeeSettlement extends FinancialSettlement {

    @Override
    public Document setAmount( BigDecimal amount ) {
        return null;
    }

    @Override
    public BigDecimal getAmount() {
        return null;
    }

    @Override
    public <T> T accept( DocumentVisitor<T> visitor ) {
        return null;
    }
}
