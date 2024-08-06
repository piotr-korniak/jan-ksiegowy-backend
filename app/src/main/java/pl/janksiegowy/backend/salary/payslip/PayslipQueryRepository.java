package pl.janksiegowy.backend.salary.payslip;

import pl.janksiegowy.backend.period.Period;

import java.util.Optional;
import java.util.UUID;

public interface PayslipQueryRepository {

    Optional<UUID> findByContractIdAndPeriod( UUID entity, Period period);
}
