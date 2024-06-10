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
        this.register= register;
        return this;
    }

    @Override public BigDecimal getAmount() {
        return getDt();
    }

    @Override public Document setAmount( BigDecimal amount ) {
        this.setDt( amount);
        return this;
    }

    @Override public InvoiceRegisterKind getRegisterKind() {
        return register.getKind();
    }
}
