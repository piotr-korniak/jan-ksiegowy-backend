package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.invoice.Invoice;

@Entity
@DiscriminatorValue( "I")
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
