package pl.janksiegowy.backend.salary;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.salary.contract.Contract;
import pl.janksiegowy.backend.salary.payslip.Payslip;

public interface SalaryService {

    Payslip save( Payslip source);
    Payslip calculatePayslip( Contract contract, MonthPeriod period);
}
