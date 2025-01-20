package pl.janksiegowy.backend.finances.document;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DiscriminatorOptions;
import pl.janksiegowy.backend.accounting.decree.DocumentDecree;
import pl.janksiegowy.backend.finances.charge.Charge;
import pl.janksiegowy.backend.finances.notice.Note;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.finances.share.Share;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.salary.Payslip;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "SETTLEMENTS")

@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
@DiscriminatorOptions( force= true)
public abstract class Document {

    @Id
    @Column( name= "DOCUMENT_ID")
    protected UUID documentId;

    private String number;

    @Column( name= "DATE")
    private LocalDate issueDate;

    @Column( name= "DUE")
    private LocalDate dueDate;

    @ManyToOne( fetch= FetchType.EAGER)
    protected pl.janksiegowy.backend.entity.Entity entity;

    protected BigDecimal dt= BigDecimal.ZERO;
    protected BigDecimal ct= BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn //( updatable= false, insertable= false)
    private MonthPeriod period;

 //   @Column( name= "PERIOD_ID")
 //   private String periodId;

    /*
    @OneToOne( mappedBy= "settlement", cascade = CascadeType.ALL)
    protected DocumentDecree decree;
*/

    @OneToOne( mappedBy= "decree", cascade= CascadeType.ALL, orphanRemoval= true)
    private DocumentDecree decree;

    public Document setDates( LocalDate date, LocalDate due) {
        this.issueDate = date;
        this.dueDate = due;
        return this;
    }

    public abstract Document setAmount( BigDecimal amount );
    public abstract BigDecimal getAmount();
    public abstract SettlementKind getKind();

    public Document setNumber( String number) {
        this.number= number;
        return this;
    }

    public abstract <T> T accept( DocumentVisitor<T> visitor);

    public interface DocumentVisitor<T> {
        T visit( Invoice invoice);
        T visit( Payment payment);
        T visit( Note note);
        T visit( Charge charge);
        T visit( Share share);
        T visit( Payslip payslip);
    }

}
