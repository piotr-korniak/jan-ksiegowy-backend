package pl.janksiegowy.backend.statement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.decree.DocumentDecree;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.finances.settlement.SettlementType;
import pl.janksiegowy.backend.period.MonthPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@DiscriminatorValue( "S")
@SecondaryTable( name= PayableStatement.TABLE_NAME,
        pkJoinColumns= @PrimaryKeyJoinColumn( name="DOCUMENT_ID"))
public class PayableStatement extends Statement {
    static final String TABLE_NAME= "SETTLEMENTS";

    @Column( name= "DATE", table= TABLE_NAME)
    private LocalDate settlementDate;

    @Column( name="TYPE", table= TABLE_NAME)
    @Enumerated( EnumType.STRING)
    private StatementType type;

    @Column( name="KIND", table= TABLE_NAME)
    @Enumerated( EnumType.STRING)
    private SettlementKind settlementKind= SettlementKind.C;

    @ManyToOne
    @JoinColumn( name="PERIOD_ID", table = TABLE_NAME)
    private MonthPeriod settlementPeriod;

    @Column( table= TABLE_NAME)
    LocalDate due;

    @Column( table= TABLE_NAME)
    private String number;

    @ManyToOne //( fetch= FetchType.EAGER)
    @JoinColumn( table= TABLE_NAME)
    private pl.janksiegowy.backend.entity.Entity entity;

    @Column( table= TABLE_NAME)
    private BigDecimal dt= BigDecimal.ZERO;

    @Column( name= "CT", table= TABLE_NAME)
    private BigDecimal liability;



    @OneToOne //( mappedBy= "settlement", cascade = CascadeType.ALL)
    @JoinColumn( table= TABLE_NAME, name = "ID", referencedColumnName = "DOCUMENT_ID")
    protected DocumentDecree decree;

}
