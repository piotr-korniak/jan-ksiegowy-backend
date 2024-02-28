package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.register.invoice.PurchaseRegister;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue( "P")
public class PurchaseInvoice extends Invoice {

    @ManyToOne
    private PurchaseRegister register;

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
