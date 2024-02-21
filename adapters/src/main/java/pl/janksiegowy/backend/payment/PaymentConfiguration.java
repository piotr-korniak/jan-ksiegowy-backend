package pl.janksiegowy.backend.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
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
                                 final PeriodRepository periods,
                                 final EntityRepository entities,
                                 final CounterRepository counters ) {
        return new PaymentFacade( new PaymentFactory( registers,
                new NumeratorFacade( numerators, new NumeratorFactory(), counters ), periods, entities),
                payments, new ClearingFactory( settlements), clearings);
    }
}
