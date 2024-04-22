package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.register.invoice.PurchaseRegister;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue( "P")
@SecondaryTable( name= Invoice.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public class PurchaseInvoice extends Invoice {

    @Enumerated( EnumType.STRING)
    private SettlementKind kind= SettlementKind.C;

    @ManyToOne
    @JoinColumn( table = TABLE_NAME)
    private PurchaseRegister register;

    @Override public Invoice setSumTotal( BigDecimal subTotal, BigDecimal taxTotal ) {
        if( InvoiceRegisterKind.W== register.getKind())
            setCt( subTotal);
        else
            setCt( subTotal.add( taxTotal ));
        return this;
    }

    @Override public BigDecimal getAmountDue() {
        return getCt();
    };

    public Invoice setRegister( PurchaseRegister register) {
        this.register = register;
        return this;
    }

    @Override
    public Document setAmount( BigDecimal amount ) {
        return null;
    }

    @Override
    public BigDecimal getAmount() {
        return null;
    }
}
