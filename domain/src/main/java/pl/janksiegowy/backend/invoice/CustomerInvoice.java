package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import pl.janksiegowy.backend.register.vat.VatSalesRegister;
import pl.janksiegowy.backend.settlement.InvoiceSettlement;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
//@Table( name= "INVOICES")
@DiscriminatorValue( "C")
//@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public class CustomerInvoice extends Invoice {

    @ManyToOne
    private VatSalesRegister vatRegister;
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
    public CustomerInvoice setVatRegister( VatSalesRegister vatRegister) {
        this.vatRegister= vatRegister;
        return this;
    }

    @Override public Invoice setSumTotal( BigDecimal sumTotal) {
        setDt( sumTotal);
        return this;
    }

    @Override
    public BigDecimal getAmountDue() {
        return settlement.getDt();
    };

}
