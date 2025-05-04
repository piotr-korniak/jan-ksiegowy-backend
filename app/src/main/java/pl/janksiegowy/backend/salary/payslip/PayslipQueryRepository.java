package pl.janksiegowy.backend.salary.payslip;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.salary.contract.Contract;
import pl.janksiegowy.backend.salary.dto.PayslipDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PayslipQueryRepository {

    Optional<PayslipDto> findByContractIdAndPeriod( UUID entity, Period period);
    List<PayslipDto> findByPeriod( MonthPeriod period);

    BigDecimal sumByTypeAndPeriodAndDueDate( Class<? extends Contract> type, MonthPeriod month, LocalDate date);

    List<PayslipDto> findByTypeAndPeriodAndDueDate( Class<? extends Contract> type, LocalDate start, LocalDate end);
}
