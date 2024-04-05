package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.invoice.Invoice;

import java.util.List;

@Entity
@DiscriminatorValue( "X")
public class InvoiceReceivable extends Receivable {

    @OneToOne( cascade= CascadeType.ALL)
    @JoinColumn( name= "DOCUMENT_ID")   //, referencedColumnName= "ID")
    @MapsId
    protected Invoice invoice;



    @Override
    public <T> T accept( SettlementVisitor<T> visitor ) {
        return null;
    }
}
