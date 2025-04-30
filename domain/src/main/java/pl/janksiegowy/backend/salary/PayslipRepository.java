package pl.janksiegowy.backend.salary;

import pl.janksiegowy.backend.salary.payslip.PayrollPayslip;

import java.util.Optional;
import java.util.UUID;

public interface PayslipRepository {

    public PayrollPayslip save(PayrollPayslip payslip);
    Optional<PayrollPayslip> findById(UUID payslipId);
}
