package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.clearing.Clearing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue( "D")
public class Receivable extends Settlement {

    @OneToMany( mappedBy = "receivable", fetch = FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Clearing> clearings= new ArrayList<>();

    @Override public <T> T accept( SettlementVisitor<T> visitor ) {
        return null;
    }

    @Override public BigDecimal getAmount() {
        return this.dt;
    }

    @Override public Settlement setAmount( BigDecimal amount ) {
        this.dt= amount;
        return this;
    }

    @Override public Settlement setClearings( int sign, List<Clearing> clearings) {
        this.clearings= clearings;
        this.ct= clearings.stream()
                .map( Clearing::getAmount)
                .reduce( BigDecimal.ZERO, BigDecimal::add);
        this.ct= sign<0 ? ct.negate(): ct;
        return this;
    }

    @Override public List<Clearing> getClearings() {
        return clearings;
    }
}
