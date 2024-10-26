package pl.janksiegowy.backend.finances.settlement;

import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementDto;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementListDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SettlementQueryRepository {

    <T> List<T> findBy(Class<T> type);

    boolean existsByNumberAndEntityTaxNumber( String number, String taxNumber);
    Optional<SettlementDto> findByNumberAndEntityTaxNumber( String number, String taxNumber);

    List<SettlementListDto> findByEntityTypeAndEntityAccountNumberOrderByEntityAccountNumberDesc(
            EntityType entityType, String accountNumber, boolean zeroBalance);
    List<SettlementListDto> findByDtNotEqualCtOrderByDateDesc( boolean zeroBalance);

    List<SettlementListDto> findByEntityTypeAndEntityAccountNumerAsAtDate(
            EntityType entityType, String accountNumber, LocalDate date, boolean zeroBalance);

    List<SettlementListDto> findByAllAsAtDate( LocalDate date, boolean zeroBalance);

    Optional<SettlementDto> findBySettlementId(UUID settlementId);
}
