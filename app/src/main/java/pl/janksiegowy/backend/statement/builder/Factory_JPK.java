package pl.janksiegowy.backend.statement.builder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.metric.Metric;
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
import pl.janksiegowy.backend.statement.formatter.FormatterService;
import pl.janksiegowy.backend.tax.TaxService;
import pl.janksiegowy.backend.tax.TaxType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class Factory_JPK implements SpecificStatement<StatementDto> {

    private final LocalDate dateApplicable= LocalDate.of( 2020, 10, 1);

    private final StatementRepository statements;
    private final EntityQueryRepository entities;
    private final NumeratorFacade numerator;
    private final TaxService taxService;
    private final FormatterService formatters;

    @Override
    public StatementDto build(Metric metric, MonthPeriod period) {
        System.err.println( "Factory_JPK build: "+  period.getId());
        if( period.getEnd().getMonthValue()% 3 == 0) {
            var statementDto= getVatStatement( metric, period.getParent())
                    .kind( StatementKind.S)   // Settlement
                    .number( "VAT-7K "+ period.getEnd().getYear()+ "K"+
                            (( period.getEnd().getMonth().getValue()- 1) / 3 + 1));
            var result=taxService.calculate( period.getParent(), TaxType.VQ)
                    .setVariable( "POWOD", new BigDecimal( statementDto.getNo()));
            return getVatStatementMap( statementDto
                    .liability( result.getVariable( "Kwota_Zobowiazania", BigDecimal.ZERO))
                    .xml( formatters.format( period, TaxType.VQ, result))
                    .patternId( formatters.getFormatterVersion( period, TaxType.VQ)), result);
        } else {
            System.err.println( "Bez zobowiązania!");
            var statementDto= getVatStatement( metric, period)
                    .kind( StatementKind.R);   // No Settlement;
            return StatementMap.create( statementDto.xml( formatters.format( period, TaxType.VQ,
                            new Interpreter().setVariable( "POWOD", new BigDecimal( statementDto.getNo()))))
                    .patternId( formatters.getFormatterVersion( period, TaxType.VQ)));
        }
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
                .revenue( entities.findByTypeAndTaxNumber( EntityType.O, metric.getRcCode()).orElseThrow())
                .date( Util.min( LocalDate.now(), period.getEnd().plusDays( 25)))
                .due( period.getEnd().plusDays( 25 ))
                .period( period)
                .type( StatementType.V);    // VAT
    }

    private StatementMap getVatStatementMap(StatementDto statementDto, Interpreter result) {
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
