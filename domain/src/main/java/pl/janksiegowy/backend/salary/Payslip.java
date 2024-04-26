package pl.janksiegowy.backend.salary;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.settlement.PayslipSettlement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@DiscriminatorValue( "Y")
@SecondaryTable( name= Payslip.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public class Payslip extends Document {
    static final String TABLE_NAME= "PAYSLIPS";

    @Column( table= TABLE_NAME)
    private BigDecimal gross;

    @Column( table= TABLE_NAME)
    private BigDecimal insuranceEmployee;

    @Column( table= TABLE_NAME)
    private BigDecimal insuranceEmployer;

    @Column( table= TABLE_NAME)
    private BigDecimal insuranceHealth;

    @Column( table= TABLE_NAME)
    private BigDecimal taxAdvance;

    public Payslip setPayable( BigDecimal payable) {
        //settlement.setCt( payable );
        return this;
    }

    public Payslip setDate( LocalDate date ) {
        //settlement.setDate( date);
        //settlement.setDue( date);
        return this;
    }

    public Payslip setNumber( String number) {
        //settlement.setNumber( number);
        return this;
    }

    @Override
    public <T> T accept( DocumentVisitor<T> visitor ) {
        return null;
    }

    @Override
    public Document setAmount( BigDecimal amount ) {
        return null;
    }

    @Override
    public BigDecimal getAmount() {
        return null;
    }
}
