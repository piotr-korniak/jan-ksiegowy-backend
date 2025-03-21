package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingFactory;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.payment.dto.PaymentDto;

@AllArgsConstructor
public class PaymentFacade {

    private final PaymentFactory payment;
    private final PaymentRepository payments;

    private final ClearingFactory clearing;
    private final ClearingRepository clearings;

    private final DecreeFacade decrees;

    public Clearing save( ClearingDto source ) {
        return clearings.save( clearing.from( source));
    }

    public Payment save( PaymentDto source ) {
        System.err.println( "Utrwalamy: "+ source.getNumber());
        return payments.save( payment.from( source));
    }

    public Payment approve( Payment payment) {
        decrees.book( payment);
        return payment;// payments.save( decrees.book( payment));
    }


}
