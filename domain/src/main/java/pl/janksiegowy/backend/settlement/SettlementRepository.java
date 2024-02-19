package pl.janksiegowy.backend.settlement;

import java.util.Optional;
import java.util.UUID;

public interface SettlementRepository {

    Optional<Settlement> findByDocument( UUID document);
}
