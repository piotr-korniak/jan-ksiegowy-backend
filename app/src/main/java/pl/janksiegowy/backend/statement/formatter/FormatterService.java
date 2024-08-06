package pl.janksiegowy.backend.statement.formatter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.tax.TaxType;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class FormatterService {

    private final List<TaxDeclarationFormatter> formatters;

    public String format( MonthPeriod period, TaxType taxType, Interpreter result){
        return getFormater( period, taxType, result).format(period, result);
    }

    public TaxDeclarationFormatter getFormater( MonthPeriod period, TaxType taxType, Interpreter result){
        return formatters.stream()
                .filter( s-> s.isApplicable( taxType)&& !s.getDateApplicable().isAfter( period.getEnd()))
                .max( Comparator.comparing( TaxDeclarationFormatter::getDateApplicable))
                .orElseThrow(()-> new IllegalArgumentException(
                        "No applicable formatter found for type: " + taxType+ " and date: "+ period.getEnd()));
    }

    public PatternId getFormatterVersion( MonthPeriod period, TaxType taxType){
        return formatters.stream()
                .filter( s-> s.isApplicable( taxType)&& !s.getDateApplicable().isAfter( period.getEnd()))
                .max( Comparator.comparing( TaxDeclarationFormatter::getDateApplicable))
                .orElseThrow(()-> new IllegalArgumentException(
                        "No applicable formatter found for type: " + taxType+ " and date: "+ period.getEnd()))
                .getPatternId();
    }
}
