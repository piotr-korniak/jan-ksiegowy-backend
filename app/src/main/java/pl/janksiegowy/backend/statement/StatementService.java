package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.statement.dto.StatementDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class StatementService {

    private final List<SpecificStatement<StatementDto>> strategies;

    public StatementDto build(Metric metric, MonthPeriod period, TaxType taxType){
        return strategies.stream()
                .filter( s-> s.isApplicable( taxType)&& !s.getDateApplicable().isAfter( period.getEnd()))
                .max( Comparator.comparing( SpecificStatement::getDateApplicable))
                .map( specificStatement -> specificStatement.build( metric, period))
                .orElseThrow();
    }
}
