package pl.janksiegowy.backend.period;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import jakarta.persistence.Entity;

@Getter

@Entity
@DiscriminatorValue( value= "M")
public class MonthPeriod extends Period {

    @ManyToOne( fetch= FetchType.EAGER)
    private QuarterPeriod parent;

    public Period setParent( QuarterPeriod period) {
        this.parent= period;
        return this;
    }
}
