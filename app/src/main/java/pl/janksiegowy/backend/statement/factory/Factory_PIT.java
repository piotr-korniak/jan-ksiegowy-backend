package pl.janksiegowy.backend.statement.factory;

import lombok.AllArgsConstructor;
import net.sf.saxon.expr.parser.Loc;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementMap;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.statement.*;
import pl.janksiegowy.backend.statement.dto.StatementDto;
import pl.janksiegowy.backend.statement.dto.StatementLineDto;
import pl.janksiegowy.backend.statement.dto.StatementMap;
import pl.janksiegowy.backend.statement.formatter.dto.FormatterDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class Factory_PIT implements FactoryStrategy<StatementDto> {

    private final LocalDate dateApplicable= LocalDate.ofYearDay( 1970, 4);
    private final MetricRepository metricRepository;
    private final StatementRepository statements;
    private final NumeratorFacade numerator;
    private final EntityQueryRepository entities;

    @Override
    public StatementDto create( MonthPeriod period, Interpreter calculation, FormatterDto formatted) {

        return metricRepository.findByDate( period.getBegin())
                .map( metric -> statements.findFirstByPatternLikeAndPeriodOrderByNoDesc( "PIT_4R%", period)
                        .filter( statement-> StatementStatus.S != statement.getStatus())
                        .map( statement-> StatementDto.create()
                                .statementId( statement.getStatementId())
                                .no( statement.getNo()))
                        .orElseGet(()-> StatementDto.create()
                                .no( Integer.parseInt( numerator.increment( NumeratorCode.ST, "PIT-4R", period.getEnd()))))
                        .revenue( entities.findByTypeAndTaxNumber( EntityType.R, metric.getRcCode()).orElseThrow())
                        .kind( StatementKind.S)
                        .number( "PIT-4R "+ period.getEnd().getYear()+
                                "M"+ String.format( "%02d", period.getEnd().getMonthValue()))
                        .liability( calculation.getVariable( "KW_ZAL", BigDecimal.ZERO))
                        .patternId( formatted.version())
                        .xml( formatted.content())
                        .date( Util.min( LocalDate.now(), period.getEnd()))
                        .due( period.getEnd().plusDays( 20 ))
                        .period( period)
                        .type( StatementType.T))    // PIT
                .map( proxy -> StatementMap.create( proxy)
                        .addLineIfNotZero( StatementLineDto.create()
                                .itemCode( StatementItemCode.PIT4_Z)
                                .amount( calculation.getVariable( "KW_ZAL", BigDecimal.ZERO))))
                .orElseThrow();
    }

    @Override public boolean isApplicable( TaxType taxType) {
        return TaxType.PM== taxType;
    }

    @Override public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
