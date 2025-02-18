package pl.janksiegowy.backend.declaration.factory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.declaration.*;
import pl.janksiegowy.backend.declaration.dto.StatementDto;
import pl.janksiegowy.backend.declaration.formatter.dto.FormatterDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class Factory_DRA implements FactoryStrategy<StatementDto, Interpreter, FormatterDto> {

    private final LocalDate dateApplicable= LocalDate.ofYearDay( 1970, 4);

    private final MetricRepository metricRepository;
    private final StatementRepository statements;
    private final NumeratorFacade numerator;
    private final EntityQueryRepository entities;

    @Override public StatementDto create(MonthPeriod period, Interpreter calculation, FormatterDto formatted) {
        return metricRepository.findByDate( period.getBegin())
                .map( metric -> statements.findFirstByPatternLikeAndPeriodOrderByNoDesc( "KEDU%", period)
                        .filter( statement-> StatementStatus.S != statement.getStatus())
                        .map( statement-> StatementDto.create()
                                .statementId( statement.getStatementId())
                                .no( statement.getNo()))
                        .orElseGet(()-> StatementDto.create()
                                .no( Integer.parseInt( numerator.increment( NumeratorCode.ST, "DRA", period.getEnd()))))
                        .revenue( entities.findByTypeAndTaxNumber( EntityType.R, metric.getRcCode()).orElseThrow())
                        .number( "DRA "+ period.getEnd().getYear()+
                                "M"+ String.format( "%02d", period.getEnd().getMonthValue()))
                        .liability( calculation.getVariable( "KW_ZOB", BigDecimal.ZERO))
                        .patternId( formatted.version())
                        .xml( formatted.content())
                        .date( Util.min( LocalDate.now(), period.getEnd()))
                        .due( period.getEnd().plusDays( 20 ))
                        .period( period)
                        .type( DeclarationType.D))    // DRA
/*                .map( proxy -> StatementMap.create( proxy)
                        .addLineIfNotZero( StatementLineDto.create()
                                .itemCode( DeclarationElementCode.UB_EME)
                                .amount( calculation.getVariable( "UB_EME", BigDecimal.ZERO)))
                        .addLineIfNotZero( StatementLineDto.create()
                                .itemCode( DeclarationElementCode.UB_REN)
                                .amount( calculation.getVariable( "UB_REN", BigDecimal.ZERO)))
                        .addLineIfNotZero( StatementLineDto.create()
                                .itemCode( DeclarationElementCode.UC_ZAT)
                                .amount( calculation.getVariable( "UC_ZAT", BigDecimal.ZERO)))
                        .addLineIfNotZero( StatementLineDto.create()
                                .itemCode( DeclarationElementCode.UW_PRA)
                                .amount( calculation.getVariable( "UW_PRA", BigDecimal.ZERO)))
                        .addLineIfNotZero( StatementLineDto.create()
                                .itemCode( DeclarationElementCode.UB_ZDR)
                                .amount( calculation.getVariable( "UB_ZDR", BigDecimal.ZERO)))
                        .addLineIfNotZero( StatementLineDto.create()
                                .itemCode( DeclarationElementCode.F_FGSP)
                                .amount( calculation.getVariable( "F_FGSP", BigDecimal.ZERO)))
                        .addLineIfNotZero( StatementLineDto.create()
                                .itemCode( DeclarationElementCode.F_FPFS)
                                .amount( calculation.getVariable( "F_FPFS", BigDecimal.ZERO)))
                        .addLineIfNotZero( StatementLineDto.create()
                                .itemCode( DeclarationElementCode.KW_ZOB)
                                .amount( calculation.getVariable( "KW_ZOB", BigDecimal.ZERO))))*/
                .orElseThrow();
    }

    @Override
    public boolean isApplicable( TaxType taxType) {
        return TaxType.ZD == taxType;
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
