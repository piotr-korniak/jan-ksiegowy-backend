package pl.janksiegowy.backend.finances.payment;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue( "R")
@SecondaryTable( name= Payment.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public class PaymentReceipt extends Payment {

    @Enumerated( EnumType.STRING)
    private SettlementKind kind= SettlementKind.C;

    @OneToMany( mappedBy = "payable", fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    private Set<Clearing> clearings= new HashSet<>();


    @Override public Document setAmount( BigDecimal amount) {
        this.ct = amount;
        return this;
    }

    @Override public BigDecimal getAmount() {
        return this.ct;
    }

    @Override
    public SettlementKind getSettlementKind() {
        return kind;
    }

    @Override public Set<Clearing> getClearings() {
        return clearings;
    }
    @Override public Payment setClearings( Set<Clearing> clearings) {
        this.clearings= clearings;
        this.dt= clearings.stream()
                .map( Clearing::getAmount)
                .reduce( BigDecimal.ZERO, BigDecimal::add);
        return this;
    }
}
