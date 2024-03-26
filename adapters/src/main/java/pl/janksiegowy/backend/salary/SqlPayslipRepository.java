package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SqlPayslipRepository extends JpaRepository<Payslip, UUID> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class PayslipRepositoryImpl implements PayslipRepository {

    private final SqlPayslipRepository repository;
    @Override public Payslip save( Payslip payslip) {
        return repository.save( payslip);
    }
}