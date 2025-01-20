package pl.janksiegowy.backend.statement.factory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.report.ReportFacade;
import pl.janksiegowy.backend.report.ReportType;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.statement.*;
import pl.janksiegowy.backend.statement.calculate.CalculateStrategy;
import pl.janksiegowy.backend.statement.dto.StatementDto;
import pl.janksiegowy.backend.statement.dto.StatementLineDto;
import pl.janksiegowy.backend.statement.dto.StatementMap;
import pl.janksiegowy.backend.statement.formatter.dto.FormatterDto;
import pl.janksiegowy.backend.tax.TaxType;
import pl.janksiegowy.backend.tax.vat.ProfitAndLossItems;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class Factory_CIT implements FactoryStrategy<StatementDto> {

    private final LocalDate dateApplicable= LocalDate.of( 1970, 1, 4);

    private final MetricRepository metricRepository;
    private final StatementRepository statements;
    private final NumeratorFacade numerator;
    private final EntityQueryRepository entities;

    @Override public StatementDto create( MonthPeriod period, Interpreter calculation, FormatterDto formatted) {
        return metricRepository.findByDate( period.getBegin())
                .map( metric-> statements.findFirstByPatternLikeAndPeriodOrderByNoDesc( "CIT%", period)
                        .filter( statement-> StatementStatus.S != statement.getStatus())
                        .map( statement-> StatementDto.create()
                                .statementId( statement.getStatementId())
                                .no( statement.getNo()))
                        .orElseGet(()-> StatementDto.create()
                                .no( Integer.parseInt( numerator
                                        .increment( NumeratorCode.ST, "CIT", period.getEnd()))))
                        .kind( StatementKind.S)
                        .type( StatementType.I)
                        .date( period.getEnd())
                        .period( period)
                        .patternId( formatted.version())
                        .xml( formatted.content())
                        .liability( calculation.getVariable( "CIT", BigDecimal.ZERO))
                        .number( "CIT-8 "+ period.getEnd().getYear()+
                                "M"+ String.format( "%02d", period.getEnd().getMonthValue()))
                        .due( period.getEnd().plusDays( 20))
                        .revenue( entities.findByTypeAndTaxNumber( EntityType.R, metric.getRcCode()).orElseThrow()))
                .map( proxy-> StatementMap.create( proxy)
                        .addLineIfNotZero( StatementLineDto.create()
                                .itemCode( StatementItemCode.CIT8_Z)
                                .amount( calculation.getVariable( "CIT", BigDecimal.ZERO))))
                .orElseThrow();
    }

    @Override public boolean isApplicable(TaxType taxType) {
        return TaxType.CM== taxType;
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
