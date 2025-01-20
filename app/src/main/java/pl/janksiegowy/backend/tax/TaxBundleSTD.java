package pl.janksiegowy.backend.tax;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.tax.CIT;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.statement.*;
import pl.janksiegowy.backend.statement.dto.StatementDto;
import pl.janksiegowy.backend.statement.dto.StatementLineDto;
import pl.janksiegowy.backend.statement.dto.StatementMap;

import java.math.BigDecimal;
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
                    taxes.add( TaxType.PM);
                    taxes.add( metric.isCitMonthly()== CIT.Yes? TaxType.CM: TaxType.CQ);
                    //if( metric.isVatMonthly().isVat())
                       /// taxes.add( TaxType.VM);
                });

        return taxes;
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }


}
