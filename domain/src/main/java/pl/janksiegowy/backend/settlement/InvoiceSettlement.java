package pl.janksiegowy.backend.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.invoice.CustomerInvoice;
import pl.janksiegowy.backend.invoice.Invoice;

import java.util.UUID;

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


    /*
    @OneToOne( mappedBy= "settlement")
    protected Invoice invoice;
*/
    /*
    public InvoiceSettlement setInvoice( Invoice document) {
        this.document= document;
        System.err.println( "UUID: "+ document.getInvoiceId());
        this.setId( document.getInvoiceId());
        return this;
    }*/

}
