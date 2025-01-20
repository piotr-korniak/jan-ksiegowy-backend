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
    @JoinColumn( table= TABLE_NAME)
    private PurchaseRegister register;

    @Override public InvoiceRegisterKind getRegisterKind() {
        return register.getKind();
    }

    public Invoice setRegister( PurchaseRegister register) {
        this.register= register;
        return this;
    }

    @Override public Document setAmount( BigDecimal amount ) {
        return setCt( amount);
    }

    @Override public BigDecimal getAmount() {
        return getCt();
    }

    @Override
    public SettlementKind getKind() {
        return kind;
    }
}
