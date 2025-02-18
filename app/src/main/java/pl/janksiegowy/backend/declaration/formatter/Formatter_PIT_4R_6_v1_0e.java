package pl.janksiegowy.backend.declaration.formatter;

import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.declaration.formatter.dto.FormatterDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

@Component
public class Formatter_PIT_4R_6_v1_0e implements FormatterStrategy<FormatterDto, Interpreter> {

    private final LocalDate dateApplicable= LocalDate.of( 2016,1, 1);

    @Override public FormatterDto format( Period period, Interpreter result) {
        return FormatterDto.create()
                .content( "Formatter_PIT_4_Zaliczka")
                .version( PatternId.PIT_4R_6_v1_0e);
    }

    @Override public boolean isApplicable(TaxType taxType) {
        return TaxType.PM== taxType;
    }

    @Override public LocalDate getDateApplicable() {
        return dateApplicable;
    }

}
