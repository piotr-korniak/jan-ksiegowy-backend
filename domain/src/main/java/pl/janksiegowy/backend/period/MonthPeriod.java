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
    private QuaterPeriod parent;

    public Period setParent( QuaterPeriod period) {
        this.parent= period;
        return this;
    }
}
