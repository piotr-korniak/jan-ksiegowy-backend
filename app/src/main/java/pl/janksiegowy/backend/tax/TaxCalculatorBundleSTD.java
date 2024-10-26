package pl.janksiegowy.backend.tax;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
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
    private final StatementService statementService;

    private final MetricRepository metrics;
    private final EntityQueryRepository entities;
    private final StatementRepository statements;

    private final FormatterService formatters;
    private final NumeratorFacade numerator;
    private final LocalDate dateApplicable= LocalDate.of(1970, 1, 1);

    @Override public List<StatementDto> calculateTaxes(MonthPeriod period) {
        System.err.println( "Tax calculation STD");

        List <StatementDto> taxes = new ArrayList<>();

        metrics.findByDate( period.getBegin()).ifPresent(
                metric-> {
                    System.err.println( "Czy VAT miesięczny: "+ metric.isVatMonthly());
                    if( metric.isVatMonthly().isVat()) {

                        //var items= taxService.calculate( period, TaxType.V);
                        taxes.add( statementService.build( metric, period, TaxType.VM));
/*
                        taxes.add( addLine( addLine( addLine( addLine( addLine( addLine( StatementMap.create(
                                statements.findFirstByPatternLikeAndPeriodOrderByNoDesc( "VAT%", period)
                                .filter( statement-> StatementStatus.S != statement.getStatus())
                                .map( statement-> StatementDto.create()
                                        .statementId( statement.getStatementId())
                                        .no( statement.getNo()))
                                .orElseGet(()-> StatementDto.create()
                                        .no( Integer.parseInt( numerator.
                                                increment( NumeratorCode.ST, "VAT", period.getEnd()))))
                                    .type( StatementType.V)    // VAT
                                    .kind( StatementKind.S)    // Settlement
                                    .number( "VAT-7 "+ period.getEnd().getYear()+
                                            "M"+ String.format( "%02d", period.getEnd().getMonthValue()))
                                    .date( Util.min( LocalDate.now(), period.getEnd().plusDays( 25)))
                                    .due( period.getEnd().plusDays( 25 ))
                                    .patternId( formatters.getFormatterVersion( period, TaxType.VM))
                                    .period( period)
                                    .liability( items.getVariable( "Kwota_Zobowiazania", BigDecimal.ZERO))
                                    .revenue( entities.findByTypeAndTaxNumber( EntityType.R, metric.getRcCode())
                                        .orElseThrow())),
                                StatementItemCode.VAT_NZ, items.getVariable( "Razem_Nalezny")),
                                StatementItemCode.VAT_NC, items.getVariable( "Razem_Naliczony")),
                                StatementItemCode.KOR_NZ, items.getVariable( "Korekta_Naleznego")),
                                StatementItemCode.KOR_NC, items.getVariable( "Korekta_Naliczonego")),
                                StatementItemCode.DO_PRZ, items.getVariable( "Do_Przeniesienia")),
                                StatementItemCode.Z_PRZ, items.getVariable( "Z_Przeniesienia")));

 */
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

    private StatementMap addLine( StatementMap statementMap, StatementItemCode itemCode, BigDecimal amount) {
        if( amount!=null && amount.signum()!= 0)
            statementMap.addLine( StatementLineDto.create().itemCode( itemCode).amount( amount));
        return statementMap;
    }
}
