package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.salary.payslip.PayslipQueryRepository;

import java.util.Optional;
import java.util.UUID;

public interface SqlPayslipRepository extends JpaRepository<Payslip, UUID> {
}

interface SqlPayslipQueryRepository extends PayslipQueryRepository, Repository<Payslip, UUID> {

    @Override
    @Query( "SELECT p.documentId FROM Payslip p WHERE p.contract.contractId= :contractId AND p.period= :period")
    Optional<UUID> findByContractIdAndPeriod( UUID contractId, Period period);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class PayslipRepositoryImpl implements PayslipRepository {

    private final SqlPayslipRepository repository;
    @Override public Payslip save( Payslip payslip) {
        return repository.save( payslip);
    }
}