package pl.janksiegowy.backend.invoice_line;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import pl.janksiegowy.backend.shared.financial.TaxRate;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.item.Item;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "INVOICES_LINES")
public class InvoiceLine {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn( name= "INVOICE_ID")
    private Invoice invoice;

    @Enumerated( EnumType.STRING)
    private TaxRate taxRate;

    @ManyToOne( fetch= FetchType.EAGER)
    private Item item;

    private BigDecimal amount;
    private BigDecimal tax;

    private BigDecimal base;
    private BigDecimal vat;
    private BigDecimal cit;

}