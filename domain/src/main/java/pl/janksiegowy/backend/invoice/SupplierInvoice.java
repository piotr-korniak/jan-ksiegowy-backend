package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import pl.janksiegowy.backend.register.vat.VatPurchaseRegister;
import pl.janksiegowy.backend.settlement.InvoiceLiability;
import pl.janksiegowy.backend.settlement.InvoiceSettlement;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
//@Table( name= "INVOICES")
@DiscriminatorValue( "S")
//@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public class SupplierInvoice extends Invoice {

    @ManyToOne
    private VatPurchaseRegister vatRegister;
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

    @Override public Invoice setSumTotal( BigDecimal sumTotal) {
        setCt( sumTotal);
        return this;
    }

    @Override
    public BigDecimal getAmountDue() {
        return settlement.getCt();
    };

    public Invoice setVatRegister( VatPurchaseRegister vatRegister) {
        this.vatRegister= vatRegister;
        return this;
    }

}
