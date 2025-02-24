package pl.janksiegowy.backend.declaration.calculate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.report.ReportFacade;
import pl.janksiegowy.backend.report.ReportType;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.indicator.IndicatorsProperties;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.declaration.CitIndicatorCode;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class Calculate_CIT implements CalculateStrategy<Interpreter> {

    private final ReportFacade reportFacade;
    private final PeriodRepository periods;
    private final IndicatorsProperties properties;

    private final LocalDate dateApplicable= LocalDate.ofYearDay( 1970, 4);


    @Override public Interpreter calculate(MonthPeriod period) {
        var result= new Interpreter();

        var test= periods.findAnnualByDate( period.getBegin())
                .map( annualPeriod-> reportFacade.calculate( ReportType.P, "Podatek",
                        annualPeriod.getBegin(), Util.previousMonthEnd( period.getEnd()))
                .getVariable( "Podatek"));

        System.out.println( "Podatek -1: "+ Util.toString( test.get()));

        return periods.findAnnualByDate( period.getBegin())
            .map( annualPeriod->
                reportFacade.calculate( ReportType.C, "CIT",
                    result.setVariable( "Stawka", properties.getCitIndicator( CitIndicatorCode.CIT_MC, period.getEnd()))
                            .setVariable( "Zaliczki", reportFacade.calculate( ReportType.P, "Podatek",
                                            annualPeriod.getBegin(), Util.previousMonthEnd( period.getEnd()))
                                    .getVariable( "Podatek")),
                                annualPeriod.getBegin(), period.getEnd()))
            .orElseGet( Interpreter::new);
    }

    @Override public boolean isApplicable( TaxType taxType) {
        return TaxType.CM== taxType;
    }

    @Override public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
