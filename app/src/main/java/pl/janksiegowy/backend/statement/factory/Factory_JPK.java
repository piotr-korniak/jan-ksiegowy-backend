package pl.janksiegowy.backend.statement.factory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
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
public class Factory_JPK implements FactoryStrategy<StatementDto> {

    private final LocalDate dateApplicable= LocalDate.of( 2020, 10, 1);
    private final MetricRepository metricRepository;
    private final EntityQueryRepository entities;
    private final StatementRepository statements;
    private final NumeratorFacade numerator;

    @Override
    public StatementDto create( MonthPeriod period, Interpreter calculation, FormatterDto formatted) {

        return metricRepository.findByDate( period.getBegin())
            .map( metric -> {
                System.err.println( "Factory_JPK build: "+  period.getId());
                if( period.getEnd().getMonthValue()% 3 == 0) {
                    var statementDto= getVatStatement( metric, period.getParent())
                            .kind( StatementKind.S)   // Settlement
                            .number( "VAT-7K "+ period.getEnd().getYear()+ "K"+
                                    (( period.getEnd().getMonth().getValue()- 1) / 3 + 1));
                    return getVatStatementMap( calculation, statementDto
                            .liability( calculation.getVariable( "Kwota_Zobowiazania", BigDecimal.ZERO))
                            .xml( formatted.content())
                            .patternId( formatted.version()));
                } else {
                    System.err.println( "Bez zobowiązania!");
                    var statementDto= getVatStatement( metric, period).kind( StatementKind.R);   // No Settlement;
                    return StatementMap.create( statementDto
                            .xml( formatted.content())
                            .patternId( formatted.version()));
                }})
            .orElseThrow();
    }

    @Override
    public boolean isApplicable( TaxType taxType) {
        return TaxType.VQ== taxType;
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }

    private StatementDto.Proxy getVatStatement( Metric metric, Period period) {

        return statements.findFirstByPatternLikeAndPeriodOrderByNoDesc( "JPK%", period)
                .filter( statement-> StatementStatus.S != statement.getStatus())
                .map( statement-> StatementDto.create()
                        .statementId( statement.getStatementId())
                        .no( statement.getNo()))
                .orElseGet(()-> StatementDto.create()
                        .no( Integer.valueOf( numerator.increment( NumeratorCode.ST, "JPK", period.getEnd()))))
                .revenue( entities.findByTypeAndTaxNumber( EntityType.R, metric.getRcCode()).orElseThrow())
                .date( Util.min( LocalDate.now(), period.getEnd()))
                .due( period.getEnd().plusDays( 25 ))
                .period( period)
                .type( StatementType.V);    // VAT
    }

    private StatementMap getVatStatementMap( Interpreter result, StatementDto statementDto) {
        return StatementMap.create( statementDto)
                .addLine( StatementLineDto.create()
                        .itemCode( StatementItemCode.VAT_NZ)
                        .amount( result.getVariable( "Razem_Nalezny", BigDecimal.ZERO)))
                .addLine( StatementLineDto.create()
                        .itemCode( StatementItemCode.VAT_NC)
                        .amount( result.getVariable( "Razem_Naliczony", BigDecimal.ZERO)))
                .addLine( StatementLineDto.create()
                        .itemCode( StatementItemCode.KOR_NZ)
                        .amount( result.getVariable( "Korekta_Naleznego", BigDecimal.ZERO)))
                .addLine( StatementLineDto.create()
                        .itemCode( StatementItemCode.KOR_NC)
                        .amount( result.getVariable( "Korekta_Naliczonego", BigDecimal.ZERO)))
                .addLine( StatementLineDto.create()
                        .itemCode( StatementItemCode.DO_PRZ)
                        .amount( result.getVariable( "Kwota_Przeniesienia", BigDecimal.ZERO)));
    }
}
