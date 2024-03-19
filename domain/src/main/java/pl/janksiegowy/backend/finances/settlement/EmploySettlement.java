package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.salary.Payslip;

@Entity
@DiscriminatorValue( "E")
public class EmploySettlement extends Settlement {

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name= "DOCUMENT_ID")
    @MapsId
    protected Payslip payslip;

    @Override public <T> T accept( SettlementVisitor<T> visitor) {
        return visitor.visit( this);
    }
}
