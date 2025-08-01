package pl.janksiegowy.backend.invoice_line;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.invoice.InvoiceLineId;
import pl.janksiegowy.backend.invoice.TaxedAmount;
import pl.janksiegowy.backend.item.Item;

import java.math.BigDecimal;
import java.util.function.Function;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "INVOICES_LINES")
public class InvoiceLine {

    @EmbeddedId
    private InvoiceLineId id;

    @ManyToOne
    @JoinColumn( name= "INVOICE_ID")
    private Invoice invoice;

    @Embedded
    private TaxedAmount amount;

    @ManyToOne( fetch= FetchType.EAGER)
    private Item item;

    //private BigInteger no;

    private BigDecimal base;
    private BigDecimal vat;
    private BigDecimal cit;

    public InvoiceLine applyIf( boolean condition, Function<InvoiceLine, InvoiceLine> function) {
        return condition? function.apply(this): this;
    }

}