package pl.janksiegowy.backend.period;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.period.tax.CIT;
import pl.janksiegowy.backend.period.tax.JPK;
import pl.janksiegowy.backend.period.tax.PIT;
import pl.janksiegowy.backend.period.tax.VAT;
import pl.janksiegowy.backend.shared.MigrationService;

import java.time.LocalDate;
import java.util.Optional;
@Log4j2

@AllArgsConstructor
public class PeriodFacade {

    private final PeriodFactory factory;
    private final PeriodRepository periods;
    private final MetricRepository metrics;
    private final MigrationService migrationService;

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadPeriods()
                .forEach( period-> {
                    counters[0]++;

                    periods.findById( factory.createId( period)).orElseGet(()-> {
                        counters[1]++;
                        return save( period);
                    });
                });

        log.warn( "Periods migration complete!");
        return "%-50s %13s".formatted("Periods migration complete, added: ", counters[1]+ "/"+ counters[0]);
    }

    public MonthPeriod findMonthPeriodOrAdd( LocalDate date){
        return periods.findMonthByDate( date)
                .orElseGet(()-> (MonthPeriod) save( PeriodDto.create()
                        .type( PeriodType.M).begin( date)));
    }

    public AnnualPeriod findAnnualPeriodOrAdd( LocalDate date){
        return periods.findAnnualByDate( date)
                .orElseGet(()-> (AnnualPeriod) save( PeriodDto.create()
                        .type( PeriodType.A).begin( date)));
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
