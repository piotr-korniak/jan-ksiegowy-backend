package pl.janksiegowy.backend.statement.calculate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.report.ReportFacade;
import pl.janksiegowy.backend.report.ReportType;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.tax.TaxType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class Calculate_CIT implements CalculateStrategy<Interpreter> {

    private final ReportFacade reportFacade;
    private final PeriodRepository periods;

    private final LocalDate dateApplicable= LocalDate.ofYearDay( 1970, 4);


    @Override public Interpreter calculate(MonthPeriod period) {
        var result= new Interpreter();

        return periods.findAnnualByDate( period.getBegin())
            .map( annualPeriod->
                reportFacade.calculate( ReportType.C, "CIT",
                    result.setVariable( "Stawka", new BigDecimal( "0.15"))
                            .setVariable( "Zaliczki", reportFacade.calculate( ReportType.P, "Podatek",
                                            annualPeriod.getBegin(), period.getEnd().minusMonths(1))
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
