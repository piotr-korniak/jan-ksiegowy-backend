package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.statement.calculate.CalculateStrategy;
import pl.janksiegowy.backend.statement.dto.StatementDto;
import pl.janksiegowy.backend.statement.factory.FactoryStrategy;
import pl.janksiegowy.backend.statement.formatter.FormatterStrategy;
import pl.janksiegowy.backend.statement.formatter.dto.FormatterDto;
import pl.janksiegowy.backend.tax.TaxService;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class StatementService {

    private final List<CalculateStrategy<Interpreter>> calculateStrategies;
    private final List<FormatterStrategy<FormatterDto>> formatterStrategies;
    private final List<FactoryStrategy<StatementDto>> factoryStrategies;

    public CalculateStrategy<Interpreter> calculateStrategy( LocalDate date, TaxType taxType){
        for (CalculateStrategy<Interpreter> strategy: calculateStrategies) {
            System.err.println( "Calculating strategy: " + strategy.getClass().getSimpleName());
        }
        return calculateStrategies.stream()
                .filter( s-> s.isApplicable( taxType)&&
                        !s.getDateApplicable().isAfter( date))
                .max( Comparator.comparing( CalculateStrategy::getDateApplicable))
                .orElseThrow();
    }

    public FormatterStrategy<FormatterDto> formatterStrategy( LocalDate date, TaxType taxType) {
        for (FormatterStrategy<FormatterDto> strategy: formatterStrategies) {
            System.err.println( "Formatter strategy: " + strategy.getClass().getSimpleName());
        }
        return formatterStrategies.stream()
                .filter( s-> s.isApplicable( taxType)&&
                        !s.getDateApplicable().isAfter( date))
                .max( Comparator.comparing( FormatterStrategy::getDateApplicable))
                .orElseThrow();
    }

    public FactoryStrategy<StatementDto> factoryStrategy(LocalDate date, TaxType taxType) {
        for (FactoryStrategy<StatementDto> strategy: factoryStrategies) {
            System.err.println( "Factory strategy: " + strategy.getClass().getSimpleName());
        }
        return factoryStrategies.stream()
                .filter( s-> s.isApplicable( taxType)&&
                        !s.getDateApplicable().isAfter( date))
                .max( Comparator.comparing( FactoryStrategy::getDateApplicable))
                .orElseThrow();
    }
}
