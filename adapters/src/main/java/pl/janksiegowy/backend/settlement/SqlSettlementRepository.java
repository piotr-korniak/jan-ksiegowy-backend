package pl.janksiegowy.backend.settlement;

import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface SqlSettlementRepository {
}

interface SqlSettlementQueryRepository extends SettlementQueryRepository, Repository<Settlement, UUID> {

}
