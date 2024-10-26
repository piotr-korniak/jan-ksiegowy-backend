package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.clearing.Clearing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue( "C")
public class Payable extends Settlement {

    @OneToMany( mappedBy = "payable", fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    private List<Clearing> clearings= new ArrayList<>();// receivable;

    @Override
    public <T> T accept( SettlementVisitor<T> visitor ) {
        return null;
    }

    @Override public BigDecimal getAmount() {
        return this.ct;
    }

    @Override public Settlement setAmount( BigDecimal amount ) {
        this.ct= amount;
        return this;
    }

    @Override public Settlement setClearings( List<Clearing> clearings) {
        this.clearings= clearings;
        this.dt= clearings.stream()
                .map( Clearing::getAmount)
                .reduce( BigDecimal.ZERO, BigDecimal::add);
        return this;
    }
}