package pl.janksiegowy.backend.salary;

public interface ContractQueryRepository {
    boolean existsByNumber( String number);
}
