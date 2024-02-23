package pl.janksiegowy.backend.finances.settlement;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface SqlSettlementRepository extends JpaRepository<Settlement, UUID> {
}

interface SqlSettlementQueryRepository extends SettlementQueryRepository, Repository<Settlement, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class SettlementRepositoryImpl implements SettlementRepository {

    private final SqlSettlementRepository repository;
    @Override public Optional<Settlement> findByDocument( UUID document) {
        return repository.findById( document);
    }
}
