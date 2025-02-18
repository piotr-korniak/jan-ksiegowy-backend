package pl.janksiegowy.backend.tax;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.declaration.*;
import pl.janksiegowy.backend.declaration.dto.StatementDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TaxCalculatorBundleJPK implements TaxBundle {

    private final StatementService statementService;
    private final MetricRepository metrics;
    private final EntityQueryRepository entities;
    private final StatementRepository statements;

    private final NumeratorFacade numerator;

    private final LocalDate dateApplicable= LocalDate.of(2018, 1, 1);

    @Override
    public List<TaxType> taxesToProcess( MonthPeriod monthPeriod ) {

        //return List.of( TaxType.CM, TaxType.PM);
        return List.of( TaxType.ZD);
    }

    //@Override
    public List<StatementDto> calculateTaxes(MonthPeriod period) {
        System.err.println( "Tax calculation JPK");

        List <StatementDto> taxes = new ArrayList<>();
        metrics.findByDate( period.getBegin()).ifPresent(
                metric-> {
                    System.err.println( "Czy VAT miesięczny: "+ metric.isVatMonthly());
/*
                    if( metric.isVatMonthly().isVat())
                        taxes.add( statementService.build( metric, period, TaxType.VM));

                    if( period.isCit())
                        taxes.add( statementService.build( metric, period, TaxType.CM));
*/
                    System.err.println( "Czy VAT kwartalny: "+ metric.isVatQuarterly());
                }
        );
        return taxes;

    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }


}
