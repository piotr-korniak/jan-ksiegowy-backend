package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( "N")
public class NoteSettlement extends FinancialSettlement {

    @Override  public <T> T accept( SettlementVisitor<T> visitor ) {
        return visitor.visit( this);
    }
}
