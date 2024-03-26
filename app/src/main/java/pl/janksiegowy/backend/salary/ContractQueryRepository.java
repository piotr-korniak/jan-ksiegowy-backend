package pl.janksiegowy.backend.salary;

import java.util.List;

public interface ContractQueryRepository {
    boolean existsByNumber( String number);

    <T> List<T> findBy( Class<T> type);

}
