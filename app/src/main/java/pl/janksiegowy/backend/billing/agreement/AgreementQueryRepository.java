package pl.janksiegowy.backend.billing.agreement;

import pl.janksiegowy.backend.billing.agreement.dto.AgreementDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AgreementQueryRepository {

    Optional<AgreementDto> findByTaxNumber( String taxNumber);
    default List<AgreementDto> findCandidates( long groupMask, LocalDate date) {
        return findCandidates( groupMask, date, "ACTIVE");
    }
    List<AgreementDto> findCandidates( long groupMask, LocalDate date, String status);

}
