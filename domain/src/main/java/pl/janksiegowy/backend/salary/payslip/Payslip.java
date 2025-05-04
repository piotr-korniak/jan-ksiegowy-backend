package pl.janksiegowy.backend.salary.payslip;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.salary.WageIndicatorCode;
import pl.janksiegowy.backend.salary.WageIndicatorConverter;
import pl.janksiegowy.backend.salary.contract.Contract;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@DiscriminatorValue( "Y")
@SecondaryTable( name= Payslip.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public class Payslip extends Document {
    static final String TABLE_NAME= "PAYSLIPS";

    @Enumerated( EnumType.STRING)
    private SettlementKind kind= SettlementKind.C;

    @ManyToOne( fetch= FetchType.LAZY)
    @JoinColumn( table= TABLE_NAME, updatable = false, insertable = false)
    private Contract contract;

    @Column( table= TABLE_NAME, name = "CONTRACT_ID")
    private UUID contractId;

    @ElementCollection
    @CollectionTable( name= "PAYSLIPS_ELEMENTS", joinColumns= @JoinColumn( name= "PAYSLIP_ID"))
    @MapKeyColumn( name= "WAGE_INDICATOR_CODE" )
    @Column( name= "AMOUNT")
    @Convert( attributeName= "key", converter= WageIndicatorConverter.class)
    private Map<WageIndicatorCode, BigDecimal> elements= new HashMap<>();

    @Override public Document setAmount( BigDecimal amount ) {
        setCt( amount);
        return this;
    }

    @Override public BigDecimal getAmount() {
        return getCt();
    }

    @Override
    public <T> T accept( DocumentVisitor<T> visitor) {
        return visitor.visit( this);
    }


}
