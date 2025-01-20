package pl.janksiegowy.backend.statement.calculate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.tax.TaxService;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class Calculate_JPK implements CalculateStrategy<Interpreter> {

    private final TaxService taxService;
    private final LocalDate dateApplicable= LocalDate.of( 2020, 10, 1);

    @Override public Interpreter calculate( MonthPeriod period) {
        return period.getEnd().getMonthValue()% 3 == 0
                ? taxService.calculate( period.getParent(), TaxType.VQ)
                : new Interpreter();
    }

    @Override public boolean isApplicable( TaxType taxType) {
        return TaxType.VQ== taxType;
    }

    @Override public LocalDate getDateApplicable() {
        return dateApplicable;
    }


}
