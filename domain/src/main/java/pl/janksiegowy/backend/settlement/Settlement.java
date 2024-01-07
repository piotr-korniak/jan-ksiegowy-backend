package pl.janksiegowy.backend.settlement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.period.MonthPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter

//@MappedSuperclass
@Entity
@Table( name= "SETTLEMENTS")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public abstract class Settlement {

    @Id
    //@UuidGenerator
    @Column( name= "DOCUMENT_ID")
    protected UUID id;
/*
    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name= "DOCUMENT_ID")   //, referencedColumnName= "ID")
    @MapsId
    protected Invoice invoice;
*/
    private LocalDate date;
    private LocalDate due;

    private String number;
/*
    @ManyToOne( fetch= FetchType.LAZY)
    private Metric metric;
*/
    @ManyToOne( fetch= FetchType.LAZY)
    private MonthPeriod period;

    private BigDecimal dt= BigDecimal.ZERO;
    private BigDecimal ct= BigDecimal.ZERO;

    @ManyToOne( fetch= FetchType.EAGER)
    protected pl.janksiegowy.backend.entity.Entity entity;
/*
    public UUID getId() {
        return id;
    }

    public void setId( UUID documentId) {
        this.id= documentId;
    }
*/
    public Settlement setEntity( pl.janksiegowy.backend.entity.Entity entity) {
        this.entity= entity;
        return this;
    }

    public Settlement setDate( LocalDate date) {
        this.date= date;
        return this;
    }

    public Settlement setDue( LocalDate due) {
        this.due= due;
        return this;
    }

    public Settlement setNumber( String number) {
        this.number= number;
        return this;
    }

    public Settlement setDt( BigDecimal dt) {
        this.dt= dt;
        return this;
    }

    public Settlement setCt( BigDecimal ct) {
        this.ct= ct;
        return this;
    }

}

