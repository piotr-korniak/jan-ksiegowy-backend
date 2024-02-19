package pl.janksiegowy.backend.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.invoice.Invoice;

@Entity
//@Table( name= "SETTLEMENTS")
@DiscriminatorValue( "I")
//@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public class InvoiceSettlement extends Settlement {


    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name= "DOCUMENT_ID")   //, referencedColumnName= "ID")
    @MapsId
    protected Invoice invoice;

    public void setInvoice( Invoice invoice) {
        this.invoice= invoice;
        this.id= invoice.getInvoiceId();
    }

    @Override public <T> T accept( SettlementVisitor<T> visitor) {
        return visitor.visit( this);
    }


}
