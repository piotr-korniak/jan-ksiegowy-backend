package pl.janksiegowy.backend.finances.clearing;

import pl.janksiegowy.backend.declaration.DeclarationType;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.finances.settlement.SettlementKind.SettlementKindVisitor;
import pl.janksiegowy.backend.finances.settlement.SettlementType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ClearingQueryRepository   {

    boolean existReceivable( String number, String taxNumber, LocalDate date);
    boolean existPayable( String number, String taxNumber, LocalDate date);

    List<ClearingDto> findByReceivableIdAndPayableType( UUID receivableId, SettlementType settlementType);
    List<ClearingDto> findByPayableIdAndReceivableType( UUID payableId, SettlementType settlementType);

    default List<ClearingDto> findByReverse( final UUID settlementId,
                                             final SettlementKind settlementKind,
                                             final SettlementType settlementType) {
        return settlementKind.accept( new SettlementKindVisitor<List<ClearingDto>>() {
            @Override public List<ClearingDto> visitDebit() {
                return findByReceivableIdAndPayableType( settlementId, settlementType);
            }
            @Override public List<ClearingDto> visitCredit() {
                return findByPayableIdAndReceivableType( settlementId, settlementType);
            }
        });
    };

    List<ClearingDto> findByPayable( UUID documentId, DeclarationType declarationType);
    List<ClearingDto> findByPayableAfterDue( UUID documentId, DeclarationType declarationType);
}
