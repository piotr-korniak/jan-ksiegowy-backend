package pl.janksiegowy.backend.billing.group;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.billing.agreement.AgreementQueryRepository;

@Configuration
public class BillingGroupConfiguration {

    @Bean
    public BillingGroupFacade billingGroupFacade(final AgreementQueryRepository agreementRepository) {
        return new BillingGroupFacade( agreementRepository);
    }
}
