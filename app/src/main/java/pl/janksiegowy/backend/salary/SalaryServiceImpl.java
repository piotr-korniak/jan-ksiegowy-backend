package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.salary.contract.Contract;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.salary.payslip.Payslip;
import pl.janksiegowy.backend.salary.strategy.SalaryStrategy;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryFactory salaryFactory;
    private final PayslipRepository repository;
    private final List<SalaryStrategy<Contract, Interpreter, PayslipDto>> strategies;

    @Override
    public Payslip save( Payslip source) {
        return repository.save( source);
    }

    @Override
    public Payslip calculatePayslip( Contract contract, MonthPeriod period) {

        var strategy = strategies.stream()
                .filter(s -> s.isApplicable(contract.getType()) &&
                        !period.getEnd().isBefore( s.getStartDate()))
                .max( Comparator.comparing( SalaryStrategy::getStartDate))
                .orElseThrow(() -> new IllegalArgumentException( "Brak strategii dla umowy i daty rozliczenia"));


        return salaryFactory.from( strategy.factory( contract, period, strategy.calculate( contract, period)));
    }
}
