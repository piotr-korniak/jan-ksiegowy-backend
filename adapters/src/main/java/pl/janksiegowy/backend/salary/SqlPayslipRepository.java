package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.salary.contract.ContractType;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.salary.payslip.Payslip;
import pl.janksiegowy.backend.salary.payslip.PayslipQueryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SqlPayslipRepository extends JpaRepository<Payslip, UUID> {
}

interface SqlPayslipQueryRepository extends PayslipQueryRepository, Repository<Payslip, UUID> {

    @Override
    @Query( "SELECT p FROM Payslip p " +
            "LEFT JOIN Clearing c ON p.payslipId= c.payableId " +
            "WHERE TYPE(p)= :type AND c.date BETWEEN :startDate AND :endDate")
    List<PayslipDto> findByTypeAndPeriodAndDueDate( Class<? extends Payslip> type,
                                                    LocalDate startDate, LocalDate endDate);

    @Override
    @Query( "SELECT p.payslipId FROM Payslip p WHERE p.contract.contractId= :contractId AND p.settlementPeriod= :period")
    Optional<UUID> findByContractIdAndPeriod( UUID contractId, Period period);

    @Override
    @Query( "SELECT coalesce( sum( c.amount), 0) FROM Payslip p " +
            "LEFT JOIN Clearing c ON p.payslipId= c.payableId " +
            "WHERE TYPE(p)= :type AND p.settlementPeriod= :month AND c.date <= :date")
    BigDecimal sumByTypeAndPeriodAndDueDate(Class<? extends Payslip> type, MonthPeriod month, LocalDate date);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class PayslipRepositoryImpl implements PayslipRepository {

    private final SqlPayslipRepository repository;
    @Override public Payslip save( Payslip payslip) {
        return repository.save( payslip);
    }

    @Override public Optional<Payslip> findById( UUID payslipId) {
        return repository.findById( payslipId);
    }
}