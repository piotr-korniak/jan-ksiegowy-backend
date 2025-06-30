package pl.janksiegowy.backend.salary;

import org.springframework.data.repository.query.Param;
import pl.janksiegowy.backend.salary.payslip.Payslip;

import java.util.Optional;
import java.util.UUID;

public interface PayslipRepository {

    public Payslip save( Payslip payslip);
    Optional<Payslip> findById( UUID payslipId);
    Optional<Payslip> findByIdWithContract( @Param( "id") UUID id);
}
