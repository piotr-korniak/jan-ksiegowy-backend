package pl.janksiegowy.backend.declaration.factory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.declaration.*;
import pl.janksiegowy.backend.declaration.dto.StatementDto;
import pl.janksiegowy.backend.declaration.formatter.dto.FormatterDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Component
@AllArgsConstructor
public class Factory_CIT implements FactoryStrategy<StatementDto, Interpreter, FormatterDto> {

    private final LocalDate dateApplicable= LocalDate.of( 1970, 1, 4);

    private final MetricRepository metricRepository;
    private final StatementRepository statements;
    private final NumeratorFacade numerator;
    private final EntityQueryRepository entities;

    @Override public StatementDto create( MonthPeriod period, Interpreter calculation, FormatterDto formatted) {
        var temp= metricRepository.findByDate( period.getBegin())
                .map( metric-> statements.findFirstByPatternLikeAndPeriodOrderByNoDesc( "CIT%", period)
                        .filter( statement-> StatementStatus.S != statement.getStatus())
                        .map( statement-> StatementDto.create()
                                .statementId( statement.getStatementId())
                                .no( statement.getNo()))
                        .orElseGet(()-> StatementDto.create()
                                .no( Integer.parseInt( numerator
                                        .increment( NumeratorCode.ST, "CIT", period.getEnd()))))
                        .type( DeclarationType.C)
                        .date( period.getEnd())
                        .period( period)
                        .patternId( formatted.version())
                        .xml( formatted.content())
                        .liability( calculation.getVariable( "CIT", BigDecimal.ZERO))
                        .number( "CIT-8 "+ period.getEnd().getYear()+
                                "M"+ String.format( "%02d", period.getEnd().getMonthValue()))
                        .due( period.getEnd().plusDays( 20))
                        .revenue( entities.findByTypeAndTaxNumber( EntityType.R, metric.getRcCode()).orElseThrow())
                        .elements( Map.of(
                                DeclarationElementCode.CIT8_Z, calculation.getVariable( "CIT", BigDecimal.ZERO))))
                .orElseThrow();
        System.err.println( "Declaration: "+ temp.getElements().getOrDefault( DeclarationElementCode.CIT8_Z,BigDecimal.ZERO));
        return temp;
    }

    @Override public boolean isApplicable(TaxType taxType) {
        return TaxType.CM== taxType;
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
