package pl.janksiegowy.backend.statement.builder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.statement.*;
import pl.janksiegowy.backend.statement.dto.StatementDto;
import pl.janksiegowy.backend.statement.dto.StatementMap;
import pl.janksiegowy.backend.tax.TaxType;
import pl.janksiegowy.backend.tax.vat.ProfitAndLossItems;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class Factory_Advance implements SpecificStatement<StatementDto> {

    private final LocalDate dateApplicable= LocalDate.of( 1970, 1, 4);

    private final ProfitAndLossItems profitAndLossItems;
    private final PeriodRepository periods;
    private final StatementRepository statements;
    private final NumeratorFacade numerator;
    private final EntityQueryRepository entities;

    
    
    @Override
    public StatementDto build(Metric metric, MonthPeriod period) {

        var inter= periods.findAnnualByDate( period.getBegin()).map( annualPeriod ->
                        profitAndLossItems.calculate( annualPeriod.getBegin(), period.getEnd()))
                .orElseGet( Interpreter::new);

        inter.setVariable( "Stawka", new BigDecimal( "0.15"));
        inter.setVariable( "JEDEN", BigDecimal.ONE);
        inter.interpret( "Podatek", "[Podstawa]* [Stawka]@ [JEDEN]");

        if( inter.getVariable( "Podatek" ).compareTo( inter.getVariable( "Zaliczki"))>0 ) {
            inter.setVariable( "Podatek",
                    inter.getVariable( "Podatek").subtract( inter.getVariable( "Zaliczki")));
        }
        System.err.println( "Zaliczki na podatek: "+ inter.getVariable( "Zaliczki"));
        System.err.println( "Aktualna zaliczka: "+ inter.getVariable( "Podatek"));

        return StatementMap.create(
                statements.findFirstByPatternLikeAndPeriodOrderByNoDesc( "CIT%", period)
                        .filter( statement-> StatementStatus.S != statement.getStatus())
                        .map( statement-> StatementDto.create()
                                .statementId( statement.getStatementId())
                                .no( statement.getNo()))
                        .orElseGet(()-> StatementDto.create()
                                .no( Integer.parseInt( numerator.
                                        increment( NumeratorCode.ST, "CIT", period.getEnd()))))
                        .kind( StatementKind.S)
                        .type( StatementType.I)
                        .date( period.getEnd())
                        .period( period)
                        .patternId( PatternId.CIT_8_33_v2_0e)
                        .liability( inter.getVariable( "Podatek"))
                        .number( "CIT-8 "+ period.getEnd().getYear()+
                                "M"+ String.format( "%02d", period.getEnd().getMonthValue()))
                        .due( period.getEnd().plusDays( 20))
                        .revenue( entities.findByTypeAndTaxNumber( EntityType.R, metric.getRcCode()).orElseThrow()));
    }

    @Override public boolean isApplicable( TaxType taxType) {
        return TaxType.CM== taxType;
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
