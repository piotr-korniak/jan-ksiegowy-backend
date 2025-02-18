package pl.janksiegowy.backend.salary.strategy;

import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.salary.contract.ContractType;

import java.time.LocalDate;

public interface SalaryStrategy<T, C, F> {
    boolean isApplicable( ContractType contractType);
    C calculate( T contract, Period period);
    F factory( T contract, Period period, C calculation);
    LocalDate getStartDate();
}
