package pl.janksiegowy.backend.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;
import pl.janksiegowy.backend.settlement.SettlementRepository;
import pl.janksiegowy.backend.shared.numerator.CounterRepository;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.shared.numerator.NumeratorFactory;
import pl.janksiegowy.backend.shared.numerator.NumeratorRepository;

@Configuration
public class PaymentConfiguration {

    @Bean
    PaymentFacade paymentFacade( final PaymentRegisterRepository registers,
                                 final PaymentRepository payments,
                                 final SettlementRepository settlements,
                                 final ClearingRepository clearings,
                                 final NumeratorRepository numerators,
                                 final CounterRepository counters ) {
        return new PaymentFacade( new PaymentFactory( registers,
                new NumeratorFacade( numerators,
                        new NumeratorFactory(), counters )), payments,
                                  new ClearingFactory( settlements), clearings);
    }
}
