package pl.janksiegowy.backend.salary.strategy;

import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.salary.ContractType;
import pl.janksiegowy.backend.contract.dto.ContractDto;
import pl.janksiegowy.backend.salary.dto.PayslipDto;

import java.time.LocalDate;

public interface SalaryStrategy {
    boolean isApplicable( ContractType contractType);
    PayslipDto calculateSalary( ContractDto contract, Period period);
    LocalDate getStartDate();
}
