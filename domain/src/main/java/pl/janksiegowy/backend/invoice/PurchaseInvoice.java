package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import pl.janksiegowy.backend.register.invoice.PurchaseRegister;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;

import java.math.BigDecimal;

@Entity
//@Table( name= "INVOICES")
@DiscriminatorValue( "P")
//@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public class PurchaseInvoice extends Invoice {

    @ManyToOne
    private PurchaseRegister register;
/*
    @OneToOne( mappedBy = "document", cascade = CascadeType.ALL)
    private InvoiceLiability settlement;

    public SupplierInvoice setSettlement( InvoiceSettlement settlement) {
        settlement.setInvoice( this);
        this.settlement= settlement;
        return this;
    }

 */
/*
    @Override
    public Invoice setNumber( String number) {
        settlement.setNumber( number);
        return this;
    }
*/

    @Override public Invoice setSumTotal( BigDecimal subTotal, BigDecimal taxTotal ) {
        if( InvoiceRegisterKind.W== register.getKind())
            setCt( subTotal);
        else
            setCt( subTotal.add( taxTotal ));
        return this;
    }

    @Override
    public BigDecimal getAmountDue() {
        return settlement.getCt();
    };

    public Invoice setRegister( PurchaseRegister register) {
        this.register = register;
        return this;
    }

}
