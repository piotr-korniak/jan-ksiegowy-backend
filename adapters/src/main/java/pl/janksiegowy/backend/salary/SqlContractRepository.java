package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.contract.ContractQueryRepository;
import pl.janksiegowy.backend.contract.dto.ContractDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SqlContractRepository extends JpaRepository<Contract, UUID> {
}

interface SqlContractQueryRepository extends ContractQueryRepository, Repository<Contract, UUID> {

    @Override
    @Query( "SELECT c FROM Contract c " +
            "WHERE c.begin <= :date AND (c.end IS NULL OR c.end >= :date)")
    List<ContractDto> findAllActive( LocalDate date);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class ContractRepositoryImpl implements ContractRepository {

    private final SqlContractRepository repository;

    @Override public Contract save( Contract contract) {
        return repository.save( contract);
    }

    @Override
    public List<Contract> findAllActive( LocalDate begin ) {
        return repository.findAll();
    }
}
