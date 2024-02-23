package pl.janksiegowy.backend.finances.settlement;

import pl.janksiegowy.backend.finances.settlement.dto.SettlementDto;

import java.util.List;
import java.util.Optional;

public interface SettlementQueryRepository {

    <T> List<T> findBy(Class<T> type);

    boolean existsByNumberAndEntityTaxNumber( String number, String taxNumber);
    Optional<SettlementDto> findByNumberAndEntityTaxNumber( String number, String taxNumber);
}
