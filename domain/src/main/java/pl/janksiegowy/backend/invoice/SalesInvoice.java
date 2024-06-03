package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.register.invoice.SalesRegister;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue( "S")
@SecondaryTable( name= Invoice.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public class SalesInvoice extends Invoice {

    @Enumerated( EnumType.STRING)
    private SettlementKind kind= SettlementKind.D;

    @Getter
    @ManyToOne
    @JoinColumn( table= TABLE_NAME)
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
        return getDt();
    };

    @Override
    public Document setAmount( BigDecimal amount ) {
        return null;
    }

    @Override
    public BigDecimal getAmount() {
        return null;
    }

}
