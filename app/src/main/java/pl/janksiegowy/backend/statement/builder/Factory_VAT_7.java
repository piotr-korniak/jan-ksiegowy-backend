package pl.janksiegowy.backend.statement.builder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.Util;
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
public class Factory_VAT_7 implements SpecificStatement<StatementDto> {

    private final LocalDate dateApplicable= LocalDate.of( 1970, 1, 4);

    private final TaxService taxService;
    private final FormatterService formatters;

    private final StatementRepository statements;
    private final NumeratorFacade numerator;
    private final MetricRepository metrics;
    private final EntityQueryRepository entities;

    @Override
    public StatementDto build(Metric metric, MonthPeriod period) {
        var items= taxService.calculate( period, TaxType.VM);

        return addLine( addLine( addLine( addLine( addLine( addLine( StatementMap.create(
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
                    .revenue( entities.findByTypeAndTaxNumber( EntityType.R, metric.getRcCode()).orElseThrow())),
                                                    StatementItemCode.VAT_NZ, items.getVariable( "Razem_Nalezny")),
                                            StatementItemCode.VAT_NC, items.getVariable( "Razem_Naliczony")),
                                    StatementItemCode.KOR_NZ, items.getVariable( "Korekta_Naleznego")),
                            StatementItemCode.KOR_NC, items.getVariable( "Korekta_Naliczonego")),
                    StatementItemCode.DO_PRZ, items.getVariable( "Do_Przeniesienia")),
            StatementItemCode.Z_PRZ, items.getVariable( "Z_Przeniesienia"));
    }

    @Override
    public boolean isApplicable( TaxType taxType) {
        return taxType==TaxType.VM;
    }

    @Override public LocalDate getDateApplicable() {
        return dateApplicable;
    }

    private StatementMap addLine(StatementMap statementMap, StatementItemCode itemCode, BigDecimal amount) {
        if( amount!=null && amount.signum()!= 0)
            statementMap.addLine( StatementLineDto.create().itemCode( itemCode).amount( amount));
        return statementMap;
    }
}
