package pl.janksiegowy.backend.salary;

import pl.janksiegowy.backend.salary.payslip.Payslip;

import java.util.Optional;
import java.util.UUID;

public interface PayslipRepository {

    public Payslip save( Payslip payslip);
    Optional<Payslip> findById( UUID payslipId);
}
