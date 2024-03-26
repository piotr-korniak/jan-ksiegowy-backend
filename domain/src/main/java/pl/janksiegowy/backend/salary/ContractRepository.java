package pl.janksiegowy.backend.salary;

import java.time.LocalDate;
import java.util.List;

public interface ContractRepository {

    Contract save( Contract contract);

    List<Contract> findAllActive( LocalDate begin);
}
