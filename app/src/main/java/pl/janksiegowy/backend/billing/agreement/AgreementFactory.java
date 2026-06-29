package pl.janksiegowy.backend.billing.agreement;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.billing.agreement.dto.AgreementDto;
import pl.janksiegowy.backend.entity.EntityRepository;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@AllArgsConstructor
public class AgreementFactory {
    private final EntityRepository entities;

    public Agreement from( AgreementDto source) {   // New Agreement or new history
        return update( source, new Agreement()
                .setAgreementId( Optional.ofNullable( source.getAgreementId()).orElseGet( UUID::randomUUID))
                .setDate( source.getDate()));
    }

    public Agreement update( AgreementDto source, Agreement agreement) {
        Optional.ofNullable( source.getEntity())
                .flatMap(entityDto-> entities.findByEntityIdAndDate(entityDto.getEntityId(), source.getDate()))
                .ifPresent( agreement::setEntity);
        return agreement
                .setBillingTypeMask( source.getBillingTypeMask());                            // Update agreement history

    }
}
