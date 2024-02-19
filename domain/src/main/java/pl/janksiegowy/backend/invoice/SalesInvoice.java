package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.register.invoice.PurchaseRegister;
import pl.janksiegowy.backend.register.invoice.SalesRegister;

import java.math.BigDecimal;

@Entity
//@Table( name= "INVOICES")
@DiscriminatorValue( "S")
//@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public class SalesInvoice extends Invoice {

    @ManyToOne
    private SalesRegister register;
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
    public Invoice setRegister( SalesRegister register) {
        this.register = register;
        return this;
    }

    @Override public Invoice setSumTotal( BigDecimal subTotal, BigDecimal taxTotal ) {
        if( InvoiceRegisterKind.W== register.getKind())
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
