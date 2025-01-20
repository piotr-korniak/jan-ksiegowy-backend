package pl.janksiegowy.backend.statement.formatter;

import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.statement.formatter.dto.FormatterDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

@Component
public class Formatter_CIT_8_25_v1_0e implements FormatterStrategy<FormatterDto> {

    private final LocalDate dateApplicable= LocalDate.of( 2017, 9, 26);

    @Override public FormatterDto format( Period period, Interpreter result) {
        return FormatterDto.create()
                .version( PatternId.CIT_8_25_v1_0e)
                .content( "CIT-8(25) v1-0E");
    }

    @Override public boolean isApplicable( TaxType taxType) {
        return TaxType.CM== taxType;
    }

    @Override public LocalDate getDateApplicable() {
        return dateApplicable;
    }

}
