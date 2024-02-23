package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.invoice_line.InvoiceLine;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.finances.settlement.InvoiceSettlement;
import pl.janksiegowy.backend.metric.Metric;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter

//@MappedSuperclass
@Entity
@Table( name= "INVOICES")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public abstract class Invoice {

//  Version Invoice > Settlement
    @Id
    //@UuidGenerator
    @Column( name= "ID")
    private UUID invoiceId;

    @OneToOne( mappedBy= "invoice", cascade = CascadeType.ALL)
    protected InvoiceSettlement settlement;

/*  Version: Settlement > Invoice
    @Id
    @Column( name= "ID")
    private UUID invoiceId;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name= "ID")
    @MapsId
    private InvoiceSettlement settlement;
*/

    @ManyToOne( fetch= FetchType.LAZY)
    private Metric metric;

    @Column( name= "DATE")
    private LocalDate invoiceDate;

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private InvoiceType type;

    @OneToMany( fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval= true)
    @OrderColumn( name= "NO")
    @JoinColumn( name= "INVOICE_ID")
    private List<InvoiceLine> lineItems;//= new HashSet<>();

    private BigDecimal subTotal= BigDecimal.ZERO;
    private BigDecimal taxTotal= BigDecimal.ZERO;

    public Invoice setInvoiceId( UUID invoiceId) {
        this.invoiceId= invoiceId;
        settlement.setInvoice( this);
        return this;
    }

    public Invoice setSettlement( InvoiceSettlement settlement) {
        this.settlement= settlement;
        settlement.setInvoice( this);
        return this;
    }

    public pl.janksiegowy.backend.entity.Entity getEntity() {
        return settlement.getEntity();
    }

    public Invoice setEntity( pl.janksiegowy.backend.entity.Entity entity) {
        settlement.setEntity( entity);
        return this;
    };

    public String getNumber() {
        return settlement.getNumber();
    }
    public Invoice setNumber( String number) {
        settlement.setNumber( number);
        return this;
    };

    public Invoice setLineItems( List<InvoiceLine> lineItems) {
        this.lineItems= lineItems;

        subTotal= lineItems.stream()
                .map( InvoiceLine::getAmount)
                .reduce( BigDecimal.ZERO, BigDecimal::add);

        taxTotal= lineItems.stream()
                .map( InvoiceLine::getTax)
                .reduce( BigDecimal.ZERO, BigDecimal::add);

//        setSumTotal( subTotal.add( taxTotal));
        setSumTotal( subTotal, taxTotal);
        return this;
    }

    public LocalDate getIssueDate() {
        return settlement.getDate();
    }
    protected Invoice setDates( LocalDate issueDate, LocalDate dueDate) {
        settlement.setDate( issueDate);
        settlement.setDue( dueDate);
        return this;
    }

    public Invoice setDates( LocalDate invoiceDate, LocalDate issueDate, LocalDate dueDate) {
        this.invoiceDate= invoiceDate;
        setDates( issueDate, dueDate);
        return this;
    }

    public abstract BigDecimal getAmountDue();

    public abstract Invoice setSumTotal( BigDecimal sumTotal, BigDecimal taxTotal );

    protected void setDt( BigDecimal dt) {
        settlement.setDt( dt);
    }

    protected void setCt( BigDecimal ct) {
        settlement.setCt( ct);
    }

    public Invoice setDates( LocalDate invoiceDate) {
        this.invoiceDate= invoiceDate;
        return this;
    }

    public Invoice setPeriod( MonthPeriod period) {
        settlement.setPeriod( period);
        return this;
    }

    public Invoice setMetric( Metric metric) {
        this.metric= metric;
        return this;
    }

}
