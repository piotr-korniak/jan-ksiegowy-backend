package pl.janksiegowy.backend.salary;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.settlement.PayslipSettlement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "PAYSLIPS")
public class Payslip {

    @Id
    @Column( name= "ID")
    private UUID payslipId;

    @OneToOne( mappedBy= "payslip", cascade = CascadeType.ALL)
    protected PayslipSettlement settlement;

    private BigDecimal gross;
    private BigDecimal insuranceEmployee;
    private BigDecimal insuranceEmployer;
    private BigDecimal insuranceHealth;
    private BigDecimal taxAdvance;

    public Payslip setInvoiceId( UUID payslipId) {
        this.payslipId= payslipId;
        settlement.setPayslip( this);
        return this;
    }

    public Payslip setPayable( BigDecimal payable) {
        settlement.setCt( payable );
        return this;
    }

    public Payslip setDate( LocalDate date ) {
        settlement.setDate( date);
        settlement.setDue( date);
        return this;
    }

    public Payslip setNumber( String number) {
        settlement.setNumber( number);
        return this;
    }

    public LocalDate getDate() {
        return settlement.getDate();
    }
}
