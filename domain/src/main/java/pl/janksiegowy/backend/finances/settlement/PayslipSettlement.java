package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.salary.Payslip;

import java.math.BigDecimal;
import java.util.List;

@Entity
@DiscriminatorValue( "E")
public class PayslipSettlement extends Settlement {

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name= "DOCUMENT_ID")
    @MapsId
    protected Payslip payslip;


    @Override public <T> T accept( SettlementVisitor<T> visitor) {
        return visitor.visit( this);
    }

    @Override
    public BigDecimal getAmount() {
        return null;
    }

    @Override
    public Settlement setAmount( BigDecimal amount ) {
        return null;
    }

    @Override
    public Settlement setClearings( int sign, List<Clearing> clearings ) {
        return null;
    }

    @Override
    public List<Clearing> getClearings() {
        return List.of();
    }
}
