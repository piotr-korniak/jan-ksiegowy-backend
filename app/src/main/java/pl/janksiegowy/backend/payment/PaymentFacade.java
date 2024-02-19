package pl.janksiegowy.backend.payment;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.payment.dto.ClearingDto;
import pl.janksiegowy.backend.payment.dto.PaymentDto;

@AllArgsConstructor
public class PaymentFacade {

    private final PaymentFactory payment;
    private final PaymentRepository payments;

    private final ClearingFactory clearing;
    private final ClearingRepository clearings;

    public Clearing save( ClearingDto source ) {
        return clearings.save( clearing.from( source));
    }

    public Payment save( PaymentDto source ) {
        return payments.save( payment.from( source));
    }
}
