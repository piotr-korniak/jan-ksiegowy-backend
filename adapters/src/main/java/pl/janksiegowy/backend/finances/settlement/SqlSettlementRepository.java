package pl.janksiegowy.backend.finances.settlement;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementListDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SqlSettlementRepository extends JpaRepository<Settlement, UUID> {
}

interface SqlSettlementQueryRepository extends SettlementQueryRepository, Repository<Settlement, UUID> {

    @Override
    @Query( "SELECT s.number AS number, s.dt AS dt, s.ct AS ct, s.date AS date, s.due AS due, " +
            "e.accountNumber AS entityAccountNumber, " +
            "e.name AS entityName FROM Settlement s JOIN s.entity e " +
            "WHERE s.dt <> s.ct AND (s.kind='D' OR s.kind='C') " +
            "ORDER BY s.date DESC")
    List<SettlementListDto> findByDtNotEqualCtOrderByDateDesc();
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class SettlementRepositoryImpl implements SettlementRepository {

    private final SqlSettlementRepository repository;
    @Override public Optional<Settlement> findByDocument( UUID document) {
        return repository.findById( document);
    }

    @Override public Settlement save( Settlement settlement) {
        return repository.save( settlement);
    }
}
