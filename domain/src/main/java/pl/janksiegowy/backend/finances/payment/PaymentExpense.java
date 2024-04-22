package pl.janksiegowy.backend.finances.payment;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue( "E")
public class PaymentExpense extends Payment {

    @Enumerated( EnumType.STRING)
    private SettlementKind kind= SettlementKind.D;

    @OneToMany( mappedBy = "receivableId", fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    private List<Clearing> clearings= new ArrayList<>();

    @Override public Document setAmount( BigDecimal amount) {
        this.dt = amount;
        return this;
    }

    @Override public BigDecimal getAmount() {
        return this.dt;
    }
}
