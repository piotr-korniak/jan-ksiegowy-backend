package pl.janksiegowy.backend.tax;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.period.MonthPeriod;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TaxBundle_V7 implements TaxBundle {

    private final LocalDate dateApplicable= LocalDate.of(2020, 10, 1);

    @Override
    public List<TaxType> taxesToProcess( MonthPeriod monthPeriod) {
        //return List.of( TaxType.VQ, TaxType.CM, TaxType.ZD);
        //return List.of( TaxType.CM);
        return List.of( TaxType.VQ, TaxType.CM);
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
