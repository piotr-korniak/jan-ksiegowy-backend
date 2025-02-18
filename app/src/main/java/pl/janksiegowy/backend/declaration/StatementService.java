package pl.janksiegowy.backend.declaration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.declaration.calculate.CalculateStrategy;
import pl.janksiegowy.backend.declaration.dto.StatementDto;
import pl.janksiegowy.backend.declaration.factory.FactoryStrategy;
import pl.janksiegowy.backend.declaration.formatter.FormatterStrategy;
import pl.janksiegowy.backend.declaration.formatter.dto.FormatterDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class StatementService {

    private final List<CalculateStrategy<Interpreter>> calculateStrategies;
    private final List<FormatterStrategy<FormatterDto, Interpreter>> formatterStrategies;
    private final List<FactoryStrategy<StatementDto, Interpreter, FormatterDto>> factoryStrategies;

    public CalculateStrategy<Interpreter> calculateStrategy( LocalDate date, TaxType taxType){
        for (CalculateStrategy<Interpreter> strategy: calculateStrategies) {
            System.err.println( "Calculating strategy: " + strategy.getClass().getSimpleName());
        }
        var test= calculateStrategies.stream()
                .filter( s-> s.isApplicable( taxType)&&
                        !s.getDateApplicable().isAfter( date))
                .max( Comparator.comparing( CalculateStrategy::getDateApplicable))
                .orElseThrow();
        System.err.println( "Calculating strategy >> " + test.getClass().getSimpleName());
        return test;
    }

    public FormatterStrategy<FormatterDto, Interpreter> formatterStrategy( LocalDate date, TaxType taxType) {
        for (FormatterStrategy<FormatterDto, Interpreter> strategy: formatterStrategies) {
            System.err.println( "Formatter strategy: " + strategy.getClass().getSimpleName());
        }
        return formatterStrategies.stream()
                .filter( s-> s.isApplicable( taxType)&&
                        !s.getDateApplicable().isAfter( date))
                .max( Comparator.comparing( FormatterStrategy::getDateApplicable))
                .orElseThrow();
    }

    public FactoryStrategy<StatementDto, Interpreter, FormatterDto> factoryStrategy(LocalDate date, TaxType taxType) {
        for (FactoryStrategy<StatementDto, Interpreter, FormatterDto> strategy: factoryStrategies) {
            System.err.println( "Factory strategy: " + strategy.getClass().getSimpleName());
        }
        return factoryStrategies.stream()
                .filter( s-> s.isApplicable( taxType)&&
                        !s.getDateApplicable().isAfter( date))
                .max( Comparator.comparing( FactoryStrategy::getDateApplicable))
                .orElseThrow();
    }
}
