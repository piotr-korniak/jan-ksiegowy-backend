package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.decree.SettlementDecree;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.period.MonthPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "SETTLEMENTS")

@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class Settlement {

    @Id
    @Column( name= "DOCUMENT_ID")
    protected UUID settlementId;

    @Enumerated( EnumType.STRING)
    private SettlementKind kind;

    @OneToMany( mappedBy = "receivable")
    private List<Clearing> receivable;
    @OneToMany( mappedBy = "payable")
    private List<Clearing> payable;

    private LocalDate date;
    private LocalDate due;

    private String number;

    @ManyToOne( fetch= FetchType.LAZY)
    private MonthPeriod period;

    private BigDecimal dt= BigDecimal.ZERO;
    private BigDecimal ct= BigDecimal.ZERO;

    @ManyToOne( fetch= FetchType.EAGER)
    protected pl.janksiegowy.backend.entity.Entity entity;

    @OneToOne( mappedBy= "settlement", cascade = CascadeType.ALL)
    protected SettlementDecree decree;

    @Enumerated( EnumType.STRING)
    @Column( insertable= false, updatable= false)
    private SettlementType type;

    public SettlementType getType() {
        return SettlementType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

    public Settlement setDecree( SettlementDecree decree ) {
        this.decree= decree;
        decree.setSettlement( this);
        return this;
    }

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
        T visit( PayslipSettlement payslip);
        T visit( ChargeSettlement charge);
        T visit( FeeSettlement fee);
        T visit( NoteSettlement note);
    }

}

