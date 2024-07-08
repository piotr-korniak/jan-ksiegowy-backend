package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.charge.FeeCharge;
import pl.janksiegowy.backend.finances.charge.LevyCharge;
import pl.janksiegowy.backend.finances.clearing.Clearing;

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
@DiscriminatorColumn( name= "KIND", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class Settlement {

    @Id
    @Column( name= "DOCUMENT_ID")
    protected UUID settlementId;

    @Enumerated( EnumType.STRING)
    @Column( insertable= false, updatable= false)
    private SettlementKind kind;

    @Enumerated( EnumType.STRING)
    @Column( insertable= false, updatable= false)
    private SettlementType type;

    private LocalDate date;
    private LocalDate due;

    private String number;
/*
    @ManyToOne( fetch= FetchType.LAZY)
    private MonthPeriod period;
*/
    protected BigDecimal dt= BigDecimal.ZERO;
    protected BigDecimal ct= BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn( updatable= false, insertable= false)
    protected pl.janksiegowy.backend.entity.Entity entity;
    @Column( name= "ENTITY_ID")
    private long entityId;

    public SettlementKind getKind() {
        return SettlementKind.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
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

    public abstract <T> T accept( SettlementVisitor<T> visitor);

    public abstract BigDecimal getAmount();
    public abstract Settlement setAmount( BigDecimal amount);

    public abstract Settlement setClearings( List<Clearing> clearings);

    public interface SettlementVisitor<T> {
        T visit( PayslipSettlement payslip);
        T visit( LevyCharge charge);
        T visit( FeeCharge fee);

    }

}

