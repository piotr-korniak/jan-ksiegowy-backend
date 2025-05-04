package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeService;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.salary.payslip.Payslip;

@AllArgsConstructor
public class SalaryFacade {

    private final ContractRepository contractRepository;
    private final SalaryService salaryService;
    private final DecreeService decreeService;


    public Payslip approval( Payslip payslip) {
        decreeService.save( decreeService.book( payslip));
        return payslip;
    }

    public String calculatePayslips( MonthPeriod period) {
        contractRepository.findAllActive( period.getBegin())
                .forEach( contract->
                        approval( salaryService.save( salaryService.calculatePayslip( contract, period))));
        return "Issued Payslips for "+ period.getId();
    }


}
