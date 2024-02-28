package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.register.invoice.SalesRegister;

import java.math.BigDecimal;

@Entity
//@Table( name= "INVOICES")
@DiscriminatorValue( "S")
//@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public class SalesInvoice extends Invoice {

    @ManyToOne
    private SalesRegister register;

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
