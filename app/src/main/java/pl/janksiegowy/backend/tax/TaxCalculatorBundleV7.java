package pl.janksiegowy.backend.tax;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.period.tax.VAT;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.statement.*;
import pl.janksiegowy.backend.statement.dto.StatementDto;
import pl.janksiegowy.backend.statement.dto.StatementLineDto;
import pl.janksiegowy.backend.statement.dto.StatementMap;
import pl.janksiegowy.backend.statement.formatter.FormatterService;
import pl.janksiegowy.backend.statement.formatter.TaxDeclarationFormatter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TaxCalculatorBundleV7 implements TaxCalculatorBundle {

    private final StatementRepository statements;
    private final MetricRepository metrics;
    private final EntityQueryRepository entities;

    private final TaxService taxService;
    private final FormatterService formatters;
    private final NumeratorFacade numerator;

 //   private final StatementFacade statementFacade;
    private final LocalDate dateApplicable= LocalDate.of(2020, 10, 1);

    private StatementDto.Proxy getVatStatement( Metric metric, Period period) {
        return statements.findFirstByPatternLikeAndPeriodOrderByNoDesc( "JPK%", period)
                .filter( statement-> StatementStatus.S != statement.getStatus())
                .map( statement-> StatementDto.create()
                        .statementId( statement.getStatementId())
                        .no( statement.getNo()))
                .orElseGet(()-> StatementDto.create()
                        .no( Integer.valueOf( numerator.increment( NumeratorCode.ST, "JPK", period.getEnd()))))
                .revenue( entities.findByTypeAndTaxNumber( EntityType.R, metric.getRcCode()).orElseThrow())
                .date( Util.min( LocalDate.now(), period.getEnd().plusDays( 25)))
                .due( period.getEnd().plusDays( 25 ))
                .period( period)
                .type( StatementType.V)    // VAT
                .kind( StatementKind.S);   // Settlement
    }

    private StatementMap getVatStatementMap( StatementDto statementDto, Interpreter result) {
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
                        .itemCode( StatementItemCode.STORNO)
                        .amount( result.getVariable( "Kwota_Przeniesienia", BigDecimal.ZERO)));
    }

    @Override
    public List<StatementDto> calculateTaxes( MonthPeriod period) {
        System.err.println( "Tax calculation V7");
        List<StatementDto> toPersist= new ArrayList<>();

        //period.isVat()

        metrics.findByDate( period.getBegin()).ifPresent( metric-> {

            if( metric.isVatQuarterly()) {

                if( period.getEnd().getMonthValue()% 3 == 0) {
                    var statementDto= getVatStatement( metric, period.getParent())
                            .number( "VAT-7K "+ period.getEnd().getYear()+ "K"+
                                    (( period.getEnd().getMonth().getValue()- 1) / 3 + 1));
                    var result=taxService.calculate( period.getParent(), TaxType.V)
                            .setVariable( "POWOD", new BigDecimal( statementDto.getNo()));
                    toPersist.add( getVatStatementMap( statementDto
                                    .liability( result.getVariable( "Kwota_Zobowiazania", BigDecimal.ZERO))
                                    .xml( formatters.format( period, TaxType.VQ, result))
                                    .patternId( formatters.getFormatterVersion( period, TaxType.VQ)), result));
                } else {
                    var statementDto= getVatStatement( metric, period);
                    toPersist.add( statementDto.xml( formatters.format( period, TaxType.VQ,
                            new Interpreter().setVariable( "POWOD", new BigDecimal( statementDto.getNo()))))
                            .patternId( formatters.getFormatterVersion( period, TaxType.VQ)));
                }
            }

        });
        //Factory_JPK_V7.create( period, invoiceLines);
        return toPersist;
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
