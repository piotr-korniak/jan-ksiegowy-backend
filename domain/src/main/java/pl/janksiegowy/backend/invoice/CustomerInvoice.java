package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.register.invoice.SalesRegister;

import java.math.BigDecimal;

@Entity
//@Table( name= "INVOICES")
@DiscriminatorValue( "C")
//@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public class CustomerInvoice extends Invoice {

    @ManyToOne
    private SalesRegister vatRegister;
/*
    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL)
    private InvoiceReceivable settlement;

    public CustomerInvoice setSettlement( InvoiceSettlement settlement) {
        settlement.setInvoice( this);
        this.settlement= settlement;
        return this;
    }
*/
/*

    @Override public Invoice setNumber(String number) {
        settlement.setNumber( number);
        return this;
    }
*/
    public CustomerInvoice setVatRegister( SalesRegister vatRegister) {
        this.vatRegister= vatRegister;
        return this;
    }

    @Override public Invoice setSumTotal( BigDecimal subTotal, BigDecimal taxTotal ) {
        if( InvoiceRegisterKind.W== vatRegister.getKind())
            setDt( subTotal);
        else
            setDt( subTotal.add( taxTotal ));
        return this;
    }

    @Override
    public BigDecimal getAmountDue() {
        return settlement.getDt();
    };

}
