package pl.janksiegowy.backend.salary.payslip;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.salary.dto.PayslipDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PayslipQueryRepository {

    Optional<UUID> findByContractIdAndPeriod( UUID entity, Period period);
    List<PayslipDto> findBySettlementPeriod( MonthPeriod period);

    BigDecimal sumByTypeAndPeriodAndDueDate( Class<? extends Payslip> type, MonthPeriod month, LocalDate date);

    List<PayslipDto> findByTypeAndPeriodAndDueDate( Class<? extends Payslip> type, LocalDate start, LocalDate end);
}
