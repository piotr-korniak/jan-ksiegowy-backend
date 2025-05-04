package pl.janksiegowy.backend.declaration.calculate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.salary.contract.EmploymentContract;
import pl.janksiegowy.backend.salary.payslip.PayslipQueryRepository;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class Calculate_DRA implements CalculateStrategy<Interpreter>{

    private final PayslipQueryRepository payslips;

    @Override public Interpreter calculate( MonthPeriod period) {
        var items= new Interpreter();

        payslips.findByTypeAndPeriodAndDueDate( EmploymentContract.class, period.getBegin(), period.getEnd())
                .forEach( payslipDto-> payslipDto.getElements()
                        .forEach((itemCode, amount)-> items.add( itemCode.name(), amount)));

        items.sum( "UB_EME", "UE_ZAT", "UE_PRA");
        items.sum( "UB_REN", "UR_ZAT", "UR_PRA");

        items.sum( "KW_ZOB", "UB_EME", "UB_REN", "UC_ZAT", "UB_ZDR", "UW_PRA", "F_FGSP", "F_FPFS");
        return items;
    }

    @Override public boolean isApplicable(TaxType taxType) {
        return TaxType.ZD== taxType;
    }

    @Override public LocalDate getDateApplicable() {
        return LocalDate.ofYearDay( 1970, 4);
    }
}
