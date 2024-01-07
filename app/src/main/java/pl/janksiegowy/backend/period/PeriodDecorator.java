package pl.janksiegowy.backend.period;

import pl.janksiegowy.backend.period.PeriodType.PeriodTypeVisitor;

public abstract class PeriodDecorator implements PeriodTypeVisitor<Period> {

    protected PeriodTypeVisitor<Period> visitor;

    public PeriodTypeVisitor<Period> setVisitor( PeriodTypeVisitor<Period> visitor) {
        this.visitor= visitor;
        return this;
    }
}
