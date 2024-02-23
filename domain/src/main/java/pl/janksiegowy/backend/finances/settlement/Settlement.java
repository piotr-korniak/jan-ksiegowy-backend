package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.period.MonthPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter

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

    @Enumerated( EnumType.STRING)
    private SettlementKind kind;

    private LocalDate date;
    private LocalDate due;

    private String number;

    @ManyToOne( fetch= FetchType.LAZY)
    private MonthPeriod period;

    private BigDecimal dt= BigDecimal.ZERO;
    private BigDecimal ct= BigDecimal.ZERO;

    @ManyToOne( fetch= FetchType.EAGER)
    protected pl.janksiegowy.backend.entity.Entity entity;

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

    public Settlement setPeriod( MonthPeriod period) {
        this.period= period;
        return this;
    }

    public Settlement setKind( SettlementKind kind) {
        this.kind= kind;
        return this;
    }

    public abstract <T> T accept( SettlementVisitor<T> visitor);

    public interface SettlementVisitor<T> {
        T visit( InvoiceSettlement invoice );
        T visit( StatementSettlement statement);
        T visit( PaymentSettlement payment);
    }

}

