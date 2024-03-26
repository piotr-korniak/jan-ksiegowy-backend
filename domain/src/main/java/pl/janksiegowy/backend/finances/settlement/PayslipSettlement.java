package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.salary.Payslip;

@Entity
@DiscriminatorValue( "E")
public class PayslipSettlement extends Settlement {

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name= "DOCUMENT_ID")
    @MapsId
    protected Payslip payslip;

    public void setPayslip( Payslip payslip) {
        this.payslip= payslip;
        this.settlementId = payslip.getPayslipId();
    }

    @Override public <T> T accept( SettlementVisitor<T> visitor) {
        return visitor.visit( this);
    }
}
