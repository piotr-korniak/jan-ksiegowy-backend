package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.invoice_line.InvoiceLine;
import pl.janksiegowy.backend.metric.Metric;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@SecondaryTable( name= Invoice.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public abstract class Invoice extends Document {
    static final String TABLE_NAME = "INVOICES";

    @ManyToOne( fetch= FetchType.LAZY)
    @JoinColumn( table= TABLE_NAME)
    private Metric metric;

    @Column( table= TABLE_NAME, name= "DATE")
    private LocalDate invoiceDate;

    public InvoiceType getType() {
        return InvoiceType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

    @OneToMany( fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval= true)
    @OrderColumn( name= "NO")
    @JoinColumn( name= "INVOICE_ID")
    private List<InvoiceLine> lineItems= new ArrayList<>();

    @Column( table= TABLE_NAME)
    private BigDecimal subTotal= BigDecimal.ZERO;

    @Column( table= TABLE_NAME)
    private BigDecimal taxTotal= BigDecimal.ZERO;

    public Invoice setLineItems( List<InvoiceLine> lineItems) {
        this.lineItems= lineItems;

        subTotal= lineItems.stream()
                .map( InvoiceLine::getAmount)
                .reduce( BigDecimal.ZERO, BigDecimal::add);

        taxTotal= lineItems.stream()
                .map( InvoiceLine::getTax)
                .reduce( BigDecimal.ZERO, BigDecimal::add);

        setSumTotal( subTotal, taxTotal);
        return this;
    }

    public abstract BigDecimal getAmountDue();

    public abstract Invoice setSumTotal( BigDecimal sumTotal, BigDecimal taxTotal );

    public Invoice setMetric( Metric metric) {
        this.metric= metric;
        return this;
    }
}
