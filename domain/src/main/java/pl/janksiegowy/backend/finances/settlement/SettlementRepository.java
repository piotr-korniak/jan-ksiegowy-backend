package pl.janksiegowy.backend.finances.settlement;

import java.util.Optional;
import java.util.UUID;

public interface SettlementRepository {

    Optional<Settlement> findByDocument( UUID document);

    Settlement save( Settlement entity);

    void delete( Settlement settlement);
}
