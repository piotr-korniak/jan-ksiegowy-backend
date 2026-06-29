package pl.janksiegowy.backend.billing.agreement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgreementRepository {

    Agreement save( Agreement agreement);
    Optional<Agreement> findAgreementByAgreementIdAndDate( UUID agreementId, LocalDate date);
    List<Agreement> findCandidates( long groupMask, LocalDate periodStart, LocalDate periodEnd);
}
