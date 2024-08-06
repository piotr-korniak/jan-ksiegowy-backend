package pl.janksiegowy.backend.period;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.period.tax.CIT;
import pl.janksiegowy.backend.period.tax.JPK;
import pl.janksiegowy.backend.period.tax.PIT;
import pl.janksiegowy.backend.period.tax.VAT;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
public class PeriodFacade {

    private final PeriodFactory factory;
    private final PeriodRepository periods;
    private final MetricRepository metrics;

    public MonthPeriod findMonthPeriodOrAdd( LocalDate date){
        return periods.findMonthByDate( date)
                .orElseGet(()-> (MonthPeriod) save( PeriodDto.create()
                        .type( PeriodType.M).begin( date)));
    }

    public Period save( PeriodDto source) {

        return periods.save( Optional.ofNullable( source.getId())
                .map( s -> factory.from( source))    // fixme
                .orElse( factory.from( source, new PeriodDecorator() {

                    @Override public Period visitAnnualPeriod() {
                        return metrics.findFirstByOrderById()
                                .map( metric-> visitor.visitAnnualPeriod()
                                        .setMin( metric.getId())
                                        .setTax( PIT.Yes, JPK.No, CIT.No, VAT.No))
                                .orElseThrow();
                    }

                    @Override public Period visitQuarterPeriod() {
                        return metrics.findByDate( source.getBegin())
                                .map( metric-> periods.findAnnualByDate( source.getBegin())
                                        .map( parent-> ((QuarterPeriod) visitor.visitQuarterPeriod())
                                                .setParent( parent)
                                                .setMin( parent.getBegin())
                                                .setMax( parent.getEnd())
                                                .setTax( PIT.No, JPK.No,
                                                         metric.isCitQuarterly(),
                                                         VAT.Yes)) // fixme //metric.isVatQuarterly()))
                                        .orElseThrow())
                                .orElseThrow();
                    }

                    @Override public Period visitMonthPeriod() {
                        return metrics.findByDate( source.getBegin())
                                .map( metric-> (metric.isTaxQuarterly()?
                                        periods.findQuarterByDate( source.getBegin())
                                                .or(()->Optional.of((QuarterPeriod)save(
                                                        PeriodDto.create()
                                                                .type( PeriodType.Q)
                                                                .begin( source.getBegin())))):
                                        periods.findAnnualByDate( source.getBegin()))
                                        .map( parent-> ((MonthPeriod)visitor.visitMonthPeriod())
                                                .setParent( parent)
                                                .setMin( parent.getBegin())
                                                .setMax( parent.getEnd())
                                                .setTax( PIT.No, JPK.Yes,
                                                         metric.isCitMonthly(),
                                                         metric.isVatMonthly()))
                                        .orElseThrow())
                                .orElseThrow();
                    }
                })));
    }

    private LocalDate min(LocalDate a, LocalDate b) {
        return a.isBefore( b)? a: b;
    }

    private LocalDate max( LocalDate a, LocalDate b) {
        return a.isAfter( b)? a: b;
    }
}
