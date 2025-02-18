package pl.janksiegowy.backend.salary;

import pl.janksiegowy.backend.salary.contract.Contract;

import java.time.LocalDate;
import java.util.List;

public interface ContractRepository {

    Contract save(Contract contract);

    List<Contract> findAllActive( LocalDate begin);
}
