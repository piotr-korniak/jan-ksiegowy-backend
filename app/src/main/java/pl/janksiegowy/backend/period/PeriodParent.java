package pl.janksiegowy.backend.period;

import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.period.PeriodType.PeriodTypeVisitor;
public abstract class PeriodParent<T> implements PeriodTypeVisitor<T>{

    protected Metric metric;

    public PeriodParent<T> setMetric(Metric metric) {
        this.metric= metric;
        return this;
    }
}
