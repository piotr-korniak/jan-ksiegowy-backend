package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( "C")
public class ChargeSettlement extends FinancialSettlement {
    @Override public <T> T accept( SettlementVisitor<T> visitor ) {
        return visitor.visit( this);
    }
}
