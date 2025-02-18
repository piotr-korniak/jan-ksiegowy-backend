package pl.janksiegowy.backend.salary.payslip;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.finances.settlement.SettlementType;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.salary.WageIndicatorConverter;
import pl.janksiegowy.backend.salary.WageIndicatorCode;
import pl.janksiegowy.backend.salary.contract.Contract;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "PAYSLIPS")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
@SecondaryTable( name= Payslip.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="DOCUMENT_ID"))
public abstract class Payslip {
    static final String TABLE_NAME = "SETTLEMENTS";

    @Id
    @Column(name = "ID")
    private UUID payslipId;

    @Column(name = "DATE", table = TABLE_NAME)
    private LocalDate settlementDate;

    @Column(name = "TYPE", table = TABLE_NAME)
    @Enumerated(EnumType.STRING)
    private SettlementType settlementType = SettlementType.Y;

    @Column(name = "KIND", table = TABLE_NAME)
    @Enumerated(EnumType.STRING)
    private SettlementKind settlementKind = SettlementKind.C;

    @ManyToOne
    @JoinColumn(name = "PERIOD_ID", table = TABLE_NAME)
    private MonthPeriod settlementPeriod;

    @Column( name= "DUE", table= TABLE_NAME)
    LocalDate settlementDue;

    @Column(table = TABLE_NAME)
    private String number;

    @ManyToOne //( fetch= FetchType.EAGER)
    @JoinColumn(table = TABLE_NAME)
    private pl.janksiegowy.backend.entity.Entity entity;

    @JoinColumn(updatable = false, insertable = false)
    @ManyToOne
    private Contract contract;

    @Column(name = "CONTRACT_ID")
    private UUID contractId;

    @Column( table= TABLE_NAME)
    private BigDecimal dt= BigDecimal.ZERO;

    @Column( table = TABLE_NAME)
    private BigDecimal ct;

    @ElementCollection
    @CollectionTable( name= "PAYSLIPS_ELEMENTS", joinColumns= @JoinColumn( name= "PAYSLIP_ID"))
    @MapKeyColumn( name= "WAGE_INDICATOR_CODE" )
    @Column( name= "AMOUNT")
    @Convert( attributeName= "key", converter= WageIndicatorConverter.class)
    private Map<WageIndicatorCode, BigDecimal> elements= new HashMap<>();

    public Payslip setDates( LocalDate date, LocalDate due) {
        this.settlementDate= date;
        this.settlementDue= due;
        return this;
    }

    public Payslip setAmount( BigDecimal amount) {
        this.ct= amount;
        return this;
    }

}
