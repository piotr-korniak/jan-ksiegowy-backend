package pl.janksiegowy.backend.declaration.formatter;

import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.declaration.formatter.dto.FormatterDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

@Component
public class Formatter_KEDU_4_0_0 implements FormatterStrategy<FormatterDto, Interpreter> {

    private final LocalDate dateApplicable= LocalDate.ofYearDay( 1970,4);

    @Override public FormatterDto format(Period period, Interpreter result) {

        return FormatterDto.create()
                .content( "DRA")
                .version( PatternId.KEDU_4_0_0);
    }

    @Override public boolean isApplicable(TaxType taxType) {
        return TaxType.ZD == taxType;
    }


    @Override public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
