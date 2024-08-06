package pl.janksiegowy.backend.salary;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.settlement.PayslipSettlement;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.statement.Statement;
import pl.janksiegowy.backend.statement.StatementLine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

    @JoinColumn( table= TABLE_NAME, updatable= false, insertable= false)
    @ManyToOne
    private Contract contract;

    @Column( table= TABLE_NAME, name= "CONTRACT_ID")
    private UUID contractId;

    @OneToMany( fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval= true)
    @JoinColumn( name= "PAYSLIP_ID")
    private List<PayslipLine> lines;

    public Payslip setLines( List<PayslipLine> lines) {
        this.lines= lines;
        return this;
    }

    @Override
    public <T> T accept( DocumentVisitor<T> visitor ) {
        return visitor.visit( this);
    }

    @Override
    public Document setAmount( BigDecimal amount ) {
        setCt( amount);
        return this;
    }

    @Override
    public BigDecimal getAmount() {
        return getCt();
    }
}
