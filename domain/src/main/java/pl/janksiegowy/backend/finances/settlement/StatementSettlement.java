package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.statement.Statement;

@Entity
@DiscriminatorValue( "S")
public class StatementSettlement extends Settlement {

    @OneToOne( cascade= CascadeType.ALL)
    @JoinColumn( name= "DOCUMENT_ID")   //, referencedColumnName= "ID")
    @MapsId
    protected Statement statement;

    public StatementSettlement setStatement( Statement statement) {
        this.statement= statement;
        this.settlementId = statement.getStatementId();
        return this;
    }

    @Override public <T> T accept( SettlementVisitor<T> visitor) {
        return visitor.visit( this );
    }
}
