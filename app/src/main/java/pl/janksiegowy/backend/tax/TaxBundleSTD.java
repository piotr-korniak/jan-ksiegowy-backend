package pl.janksiegowy.backend.tax;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TaxBundleSTD implements TaxBundle {

    private final MetricRepository metrics;
    private final LocalDate dateApplicable= LocalDate.of(1970, 1, 1);

    @Override
    public List<TaxType> taxesToProcess( MonthPeriod period) {
        List<TaxType> taxes= new ArrayList<>();
        metrics.findByDate( period.getBegin())
                .ifPresent( metric -> {
              //      taxes.add( TaxType.PM);
             //       taxes.add( metric.isCitMonthly()== CIT.Yes? TaxType.CM: TaxType.CQ);
                    //if( metric.isVatMonthly().isVat())
                       /// taxes.add( TaxType.VM);
                    taxes.add( TaxType.ZD);
                });

        return taxes;
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }


}
