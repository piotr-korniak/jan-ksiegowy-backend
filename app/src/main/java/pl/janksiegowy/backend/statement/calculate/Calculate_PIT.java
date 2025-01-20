package pl.janksiegowy.backend.statement.calculate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.salary.payslip.PayslipQueryRepository;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class Calculate_PIT implements CalculateStrategy<Interpreter> {

    private final PayslipQueryRepository payslips;

    @Override
    public Interpreter calculate(MonthPeriod period) {
        var items= new Interpreter();

        payslips.findByPeriod( period)
                .forEach( payslipDto-> payslipDto.getLines()
                        .forEach( payslipLine->
                                items.add( payslipLine.getItemCode().name(), payslipLine.getAmount())));
        return items;
    }

    @Override
    public boolean isApplicable( TaxType taxType) {
        return TaxType.PM== taxType;
    }

    @Override
    public LocalDate getDateApplicable() {
        return LocalDate.ofYearDay( 1970, 4);
    }
}
