package pl.janksiegowy.backend.billing.agreement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.billing.agreement.dto.AgreementCsv;
import pl.janksiegowy.backend.billing.agreement.dto.AgreementDto;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.shared.MigrationService;

import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
public class AgreementFacade {

    private final AgreementFactory factory;
    private final MigrationService migrationService;
    private final AgreementRepository repository;
    private final EntityQueryRepository entities;
    private final AgreementQueryRepository agreements;

    public Agreement save( AgreementDto source) {
        return repository.save( Optional.ofNullable( source.getAgreementId())
                .map( agreementId-> repository.findAgreementByAgreementIdAndDate( agreementId, source.getDate())
                        .map( agreement-> factory.update( source, agreement))   // Update Agreement history
                        .orElse( factory.from( source)))                                  // New Agreement history
                .orElse( factory.from( source))                                           // New Agreement
        );
    }

    public String migrate() {

        migrationService.loadAgreements().forEach( agreement-> {
            var findAgreement= agreements.findByTaxNumber( agreement.getTaxNumber());

            System.err.println( agreement.getBillingTypeMask());

            if( findAgreement.map( existing-> !existing.getDate().equals( agreement.getDate())).
                    orElse(true)) {

                save( entities.findByTypeAndTaxNumber( EntityType.C, agreement.getTaxNumber())
                        .map( entityDto-> fromCsv( agreement).entity( entityDto))
                        .orElseThrow(()-> new NoSuchElementException(
                                "Not found contact with tax number: " + agreement.getTaxNumber())));
            }
        });

        return "Agreement migrated";
    }

    private AgreementDto.Proxy fromCsv( AgreementCsv agreement) {
        return AgreementDto.create()
                .date( agreement.getDate())
                .billingTypeMask( agreement.getBillingTypeMask());
    }
}
