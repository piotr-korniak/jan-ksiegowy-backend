package pl.janksiegowy.backend.salary;

import pl.janksiegowy.backend.salary.dto.ContractDto;

import java.time.LocalDate;
import java.util.List;

public interface ContractQueryRepository {
    boolean existsByNumber( String number);

    <T> List<T> findBy( Class<T> type);

    List<ContractDto> findAllActive( LocalDate date);
}
