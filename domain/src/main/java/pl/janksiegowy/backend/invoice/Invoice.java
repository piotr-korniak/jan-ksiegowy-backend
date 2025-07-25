package pl.janksiegowy.backend.invoice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.invoice_line.InvoiceLine;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.shared.financial.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@SecondaryTable( name= Invoice.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public abstract class Invoice extends Document {
    static final String TABLE_NAME= "INVOICES";

    @ManyToOne( fetch= FetchType.LAZY)
    @JoinColumn( table= TABLE_NAME)
    private Metric metric;

    @Column( table= TABLE_NAME, name= "DATE")
    private LocalDate invoiceDate;

    @Column( table= TABLE_NAME, name= "REGISTER_ID", updatable= false, insertable= false)
    protected UUID registerId;

    /*
    @ManyToOne
    @JoinColumn( table= TABLE_NAME, name= "PERIOD_ID", updatable= false, insertable= false)
    private MonthPeriod invoicePeriod;

    @Column( table= TABLE_NAME, name= "PERIOD_ID")
    private String invoicePeriodId;
*/
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

    @Column( table= TABLE_NAME)
    private String xml;

    @Column( table= TABLE_NAME)
    @Enumerated( EnumType.ORDINAL )
    private PaymentMethod paymentMethod;

    @Column( table= TABLE_NAME)
    @Enumerated( EnumType.STRING)
    private InvoiceStatus status;

    @Column( table= TABLE_NAME)
    private String correction;

    public Invoice setLineItems( List<InvoiceLine> lineItems) {
        this.lineItems= lineItems;

        subTotal= lineItems.stream()
                .map( InvoiceLine::getAmount)
                .reduce( BigDecimal.ZERO, BigDecimal::add);

        taxTotal= lineItems.stream()
                .map( InvoiceLine::getTax)
                .reduce( BigDecimal.ZERO, BigDecimal::add);

        return this;
    }

    public abstract InvoiceRegisterKind getRegisterKind();

    public Invoice setMetric( Metric metric) {
        this.metric= metric;
        return this;
    }

    @Override public <T> T accept( DocumentVisitor<T> visitor) {
        return visitor.visit( this);
    }

    public boolean isValidated() {
        return getAmount().compareTo( getTotal())== 0;
    }

    private BigDecimal getTotal() {
        return InvoiceRegisterKind.D == getRegisterKind()? subTotal.add( taxTotal): subTotal;
    }

    public Invoice setStatus( InvoiceStatus status) {
        this.status= status;
        return this;
    }
}
