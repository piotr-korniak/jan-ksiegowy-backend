package pl.janksiegowy.backend.tax;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.statement.*;
import pl.janksiegowy.backend.statement.dto.StatementDto;
import pl.janksiegowy.backend.statement.dto.StatementLineDto;
import pl.janksiegowy.backend.statement.dto.StatementMap;
import pl.janksiegowy.backend.statement.formatter.FormatterService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TaxCalculatorBundleSTD implements TaxCalculatorBundle {

    private final TaxService taxService;
    private final FormatterService formatter;

    private final MetricRepository metrics;
    private final EntityQueryRepository entities;
    private final StatementRepository statements;
    private final NumeratorFacade numerator;
    private final LocalDate dateApplicable= LocalDate.of(1970, 1, 1);
/*
    private void save( MonthPeriod period, StatementDto source) {
        facade.approve( facade.save( period, source));
    }
*/
    @Override public List<StatementDto> calculateTaxes(MonthPeriod period) {
        System.err.println( "Tax calculation STD");

        List <StatementDto> taxes = new ArrayList<>();

        metrics.findByDate( period.getBegin()).ifPresent(
                metric-> {
                    System.err.println( "Czy VAT miesięczny: "+ metric.isVatMonthly());
                    if( metric.isVatMonthly().isVat()) {

                        var items= taxService.calculate( period, TaxType.V);
                        formatter.format( period, TaxType.V, items);


                        var version= PatternId.VAT_7_17_1_0e;
                        taxes.add( StatementMap.create(
                                statements.findFirstByPatternLikeAndPeriodOrderByNoDesc( version.toString(), period)
                                .filter( statement-> StatementStatus.S != statement.getStatus())
                                .map( statement-> StatementDto.create()
                                        .statementId( statement.getStatementId())
                                        .no( statement.getNo()))
                                .orElseGet(()-> StatementDto.create()
                                        .no( Integer.valueOf(  numerator.
                                                increment( NumeratorCode.ST, "VAT", period.getEnd()))))
                                    .type( StatementType.V)    // VAT
                                    .kind( StatementKind.S)    // Settlement
                                    .number( "VAT-7 "+ period.getEnd().getYear()+
                                            "M"+ String.format( "%02d", period.getEnd().getMonthValue()))
                                    .date( Util.min( LocalDate.now(), period.getEnd().plusDays( 25)))
                                    .due( period.getEnd().plusDays( 25 ))
                                    .patternId( version)
                                    .period( period)
                                    .liability( items.getVariable( "Kwota_Zobowiazania", BigDecimal.ZERO))
                                    .revenue( entities.findByTypeAndTaxNumber( EntityType.R, metric.getRcCode())
                                        .orElseThrow()))

                                .addLine( StatementLineDto.create()
                                        .itemCode( StatementItemCode.VAT_NZ)
                                        .amount( items.getVariable( "Razem_Nalezny", BigDecimal.ZERO)))
                                .addLine( StatementLineDto.create()
                                        .itemCode( StatementItemCode.VAT_NC)
                                        .amount( items.getVariable( "Razem_Naliczony", BigDecimal.ZERO)))
                                .addLine( StatementLineDto.create()
                                        .itemCode( StatementItemCode.KOR_NZ)
                                        .amount( items.getVariable( "Korekta_Naleznego", BigDecimal.ZERO)))
                                .addLine( StatementLineDto.create()
                                        .itemCode( StatementItemCode.KOR_NC)
                                        .amount( items.getVariable( "Korekta_Naliczonego", BigDecimal.ZERO)))
                                .addLine( StatementLineDto.create()
                                        .itemCode( StatementItemCode.STORNO)
                                        .amount( items.getVariable( "Kwota_Przeniesienia", BigDecimal.ZERO))));

                    }

                    System.err.println( "Czy VAT kwartalny: "+ metric.isVatQuarterly());
                    //if( metric.isTaxQuarterly())
                        //vatItems.calculate( period.getParent());
                }
        );
        return taxes;
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
