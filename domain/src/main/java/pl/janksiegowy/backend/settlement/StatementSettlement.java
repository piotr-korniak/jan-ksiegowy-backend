package pl.janksiegowy.backend.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.invoice.Invoice;
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
        this.id= statement.getStatementId();
        return this;
    }
}
