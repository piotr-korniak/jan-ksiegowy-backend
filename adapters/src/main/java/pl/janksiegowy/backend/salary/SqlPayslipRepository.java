package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.salary.payslip.PayrollPayslip;
import pl.janksiegowy.backend.salary.payslip.PayslipQueryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SqlPayslipRepository extends JpaRepository<PayrollPayslip, UUID> {
}

interface SqlPayslipQueryRepository extends PayslipQueryRepository, Repository<PayrollPayslip, UUID> {

    @Override
    @Query( "SELECT p FROM PayrollPayslip p " +
            "LEFT JOIN Clearing c ON p.payslipId= c.payableId " +
            "WHERE TYPE(p)= :type AND c.date BETWEEN :startDate AND :endDate")
    List<PayslipDto> findByTypeAndPeriodAndDueDate( Class<? extends PayrollPayslip> type,
                                                    LocalDate startDate, LocalDate endDate);

    @Override
    @Query( "SELECT p FROM PayrollPayslip p WHERE p.contract.contractId= :contractId AND p.settlementPeriod= :period")
    Optional<PayslipDto> findByContractIdAndPeriod( UUID contractId, Period period);

    @Override
    @Query( "SELECT coalesce( sum( c.amount), 0) FROM PayrollPayslip p " +
            "LEFT JOIN Clearing c ON p.payslipId= c.payableId " +
            "WHERE TYPE(p)= :type AND p.settlementPeriod= :month AND c.date <= :date")
    BigDecimal sumByTypeAndPeriodAndDueDate(Class<? extends PayrollPayslip> type, MonthPeriod month, LocalDate date);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class PayslipRepositoryImpl implements PayslipRepository {

    private final SqlPayslipRepository repository;
    @Override public PayrollPayslip save(PayrollPayslip payslip) {
        return repository.save( payslip);
    }

    @Override public Optional<PayrollPayslip> findById(UUID payslipId) {
        return repository.findById( payslipId);
    }
}