package pl.janksiegowy.backend.billing.agreement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.shared.MigrationService;

@Configuration
public class AgreementConfiguration {

    @Bean
    public AgreementFacade agreementFacade( final MigrationService migrationService,
                                            final AgreementRepository agreementRepository,
                                            final EntityRepository entityRepository,
                                            final EntityQueryRepository entityQueryRepository,
                                            final AgreementQueryRepository agreements) {
        return new AgreementFacade( new AgreementFactory( entityRepository),
                migrationService, agreementRepository, entityQueryRepository, agreements);
    }

}
