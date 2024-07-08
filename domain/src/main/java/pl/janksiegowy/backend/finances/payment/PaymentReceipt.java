package pl.janksiegowy.backend.finances.payment;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue( "R")
@SecondaryTable( name= Payment.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public class PaymentReceipt extends Payment {

    @Enumerated( EnumType.STRING)
    private SettlementKind kind= SettlementKind.C;

    @OneToMany( mappedBy = "payableId", fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    private List<Clearing> clearings= new ArrayList<>();


    @Override public Document setAmount( BigDecimal amount) {
        this.ct = amount;
        return this;
    }

    @Override public BigDecimal getAmount() {
        return this.ct;
    }

    @Override public List<Clearing> getClearings() {
        return clearings;
    }
    @Override public Payment setClearings( List<Clearing> clearings) {
        this.clearings= clearings;
        this.dt= clearings.stream()
                .map( Clearing::getAmount)
                .reduce( BigDecimal.ZERO, BigDecimal::add);
        return this;
    }
}
