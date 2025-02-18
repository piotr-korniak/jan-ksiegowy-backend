package pl.janksiegowy.backend.declaration.formatter;

import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

@Component
public class Formatter_VAT_7_17_1_0e implements FormatterStrategy<String, Interpreter> {

    private final LocalDate dateApplicable= LocalDate.of( 2017, 1, 1);

    @Override public boolean isApplicable( TaxType taxType) {
        return taxType== TaxType.VM;
    }

    @Override public LocalDate getDateApplicable() {
        return dateApplicable;
    }

    @Override public String format( Period period, Interpreter result) {
        return "";
    }

    /*
    @Override public PatternId getPatternId() {
        return PatternId.VAT_7_17_1_0e;
    }*/
}
