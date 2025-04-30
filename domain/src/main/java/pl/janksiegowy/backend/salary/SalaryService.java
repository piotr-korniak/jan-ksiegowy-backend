package pl.janksiegowy.backend.salary;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.salary.contract.Contract;
import pl.janksiegowy.backend.salary.payslip.PayrollPayslip;

public interface SalaryService {

    PayrollPayslip save(PayrollPayslip source);
    PayrollPayslip calculatePayslip(Contract contract, MonthPeriod period);
}
