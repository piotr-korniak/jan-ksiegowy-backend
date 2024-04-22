package pl.janksiegowy.backend.finances.document;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DiscriminatorOptions;
import pl.janksiegowy.backend.accounting.decree.DocumentDecree;
import pl.janksiegowy.backend.finances.payment.PaymentType;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;

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

    private LocalDate date;
    private LocalDate due;

    @ManyToOne( fetch= FetchType.EAGER)
    protected pl.janksiegowy.backend.entity.Entity entity;

    protected BigDecimal dt= BigDecimal.ZERO;
    protected BigDecimal ct= BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn( updatable= false, insertable= false)
    private MonthPeriod period;
    @Column( name= "PERIOD_ID")
    private String periodId;
/*
    public DocumentType getType() {
        return DocumentType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }*/

    @OneToOne( mappedBy= "settlement", cascade = CascadeType.ALL)
    protected DocumentDecree decree;

    public Document setDates( LocalDate date, LocalDate due) {
        this.date= date;
        this.due= due;
        return this;
    }

    public abstract Document setAmount( BigDecimal amount );
    public abstract BigDecimal getAmount();

    public Document setNumber( String number) {
        this.number= number;
        return this;
    }

}
