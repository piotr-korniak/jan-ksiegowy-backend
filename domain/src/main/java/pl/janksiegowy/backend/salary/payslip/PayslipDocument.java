package pl.janksiegowy.backend.salary.payslip;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@DiscriminatorValue( "Y")
public class PayslipDocument extends Document {

    @Enumerated( EnumType.STRING)
    private SettlementKind kind= SettlementKind.C;

    @Override public Document setAmount( BigDecimal amount ) {
        setCt( amount);
        return this;
    }

    @Override public BigDecimal getAmount() {
        return getCt();
    }

    @Override public <T> T accept( DocumentVisitor<T> visitor ) {
        return visitor.visit( this);
    }

}
