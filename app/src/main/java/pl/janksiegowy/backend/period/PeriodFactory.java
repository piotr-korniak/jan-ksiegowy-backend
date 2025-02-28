package pl.janksiegowy.backend.period;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.period.PeriodType.PeriodTypeVisitor;
import pl.janksiegowy.backend.metric.MetricRepository;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@AllArgsConstructor
public class PeriodFactory implements PeriodTypeVisitor<Period>{

    private final MetricRepository metrics;

    public Period getParent(){
        return null;
    }

    public String createId( PeriodDto source) {
        var begin= source.getBegin();

        return switch (source.getType()) {
            case A -> String.format( "%dA00", begin.getYear());
            case Q -> String.format( "%dQ%02d", begin.getYear(), begin.get( IsoFields.QUARTER_OF_YEAR));
            case M -> String.format( "%dM%02d", begin.getYear(), begin.getMonthValue());
        };
    }

    public Period from( PeriodDto source, PeriodDecorator decorator) {

        var periodType= source.getType();
        //var metric= metrics.findByDate( source.getBegin()).orElseThrow();
//        var parent= periodType.accept( periodParent.setMetric( metric));

        return periodType.accept( decorator.setVisitor( new PeriodTypeVisitor<Period>() {
            @Override public Period visitAnnualPeriod() {
                return new AnnualPeriod()
                        .setId( createId( source))
                        .setBegin( source.getBegin().with( TemporalAdjusters.firstDayOfYear()))
                        .setEnd( source.getEnd());
            }

            @Override public Period visitQuarterPeriod() {
                LocalDate begin= source.getBegin();
                begin= begin.with( begin.getMonth().firstMonthOfQuarter()).with( firstDayOfMonth());
                return new QuarterPeriod()
                        .setId( createId( source))
                        .setBegin( begin)
                        .setEnd( begin.plusMonths( 2).with( lastDayOfMonth()));
            }

            @Override public Period visitMonthPeriod() {
                return new MonthPeriod()
                        .setId( createId( source))
                        .setBegin( source.getBegin().with( firstDayOfMonth()))
                        .setEnd( source.getBegin().with( lastDayOfMonth()));
            }
        }));
    }

    private LocalDate min( LocalDate a, LocalDate b) {
        return a.isBefore( b)? a: b;
    }

    private LocalDate max( LocalDate a, LocalDate b) {
        return a.isAfter( b)? a: b;
    }


    public Period from( PeriodDto source) {
        return source.getType().accept( this)
                .setId( source.getId());
    }

    @Override
    public Period visitAnnualPeriod() {
        return new AnnualPeriod();
    }

    @Override
    public Period visitQuarterPeriod() {
        return new QuarterPeriod();
    }

    @Override
    public Period visitMonthPeriod() {
        return new MonthPeriod() ;
    }
}
