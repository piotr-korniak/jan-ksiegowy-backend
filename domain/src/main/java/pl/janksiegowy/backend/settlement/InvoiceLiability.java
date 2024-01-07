package pl.janksiegowy.backend.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.invoice.CustomerInvoice;
import pl.janksiegowy.backend.invoice.SupplierInvoice;

//@Entity
@Table( name= "SETTLEMENTS")
@DiscriminatorValue( "L")
//@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public class InvoiceLiability {//extends Settlement {

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name= "DOCUMENT_ID")   //, referencedColumnName= "ID")
    @MapsId
    private SupplierInvoice document;

    public InvoiceLiability setInvoice( SupplierInvoice document) {
        this.document= document;
        //this.setId( document.getInvoiceId());
        return this;
    }

}
