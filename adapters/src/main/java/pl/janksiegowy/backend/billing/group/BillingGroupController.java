package pl.janksiegowy.backend.billing.group;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.subdomain.DomainController;

import java.util.UUID;

@DomainController
@RequestMapping( "/v2/billing")
@AllArgsConstructor
public class BillingGroupController {

    private final BillingGroupFacade  billingGroupFacade;

    @GetMapping
    public ResponseEntity<?> geCompleteGroup() {

        billingGroupFacade.completeGroup( UUID.randomUUID());

        return ResponseEntity.ok().build();
    }
}
