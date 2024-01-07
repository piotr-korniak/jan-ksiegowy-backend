package pl.janksiegowy.backend.period;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.period.PeriodType.PeriodTypeVisitor;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.tax.CIT;
import pl.janksiegowy.backend.period.tax.JPK;
import pl.janksiegowy.backend.period.tax.PIT;
import pl.janksiegowy.backend.period.tax.VAT;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@AllArgsConstructor
public class PeriodFactory {

    private final MetricRepository metrics;

    public Period getParent(){
        return null;
    }

    public Period from( PeriodDto source, PeriodDecorator decorator) {

        var periodType= source.getType();
        //var metric= metrics.findByDate( source.getBegin()).orElseThrow();
//        var parent= periodType.accept( periodParent.setMetric( metric));

        return periodType.accept( decorator.setVisitor( new PeriodTypeVisitor<Period>() {
            @Override public Period visitAnnualPeriod() {
                return new AnnualPeriod()
                        .setId( source.getBegin().getYear()+ "A00")
                        .setBegin( source.getBegin().with( TemporalAdjusters.firstDayOfYear()))
                        .setEnd( source.getEnd());
            }

            @Override public Period visitQuarterPeriod() {
                LocalDate begin= source.getBegin();
                begin= begin.with( begin.getMonth().firstMonthOfQuarter())
                        .with( TemporalAdjusters.firstDayOfMonth());
                return new QuaterPeriod()
                        .setId( begin.getYear()+ "Q"+
                                String.format( "%02d", begin.get( IsoFields.QUARTER_OF_YEAR)))
                        .setBegin( begin)
                        .setEnd( begin.plusMonths( 2)
                                .with( TemporalAdjusters.lastDayOfMonth()));
            }

            @Override public Period visitMonthPeriod() {
                LocalDate begin= source.getBegin();
                return new MonthPeriod()
                        .setId( begin.getYear()+ "M"+
                                String.format( "%02d", begin.getMonthValue()))
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


}
