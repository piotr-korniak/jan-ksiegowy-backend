package pl.janksiegowy.backend.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;
import pl.janksiegowy.backend.settlement.SettlementRepository;

@Configuration
public class PaymentConfiguration {

    @Bean
    PaymentFacade paymentFacade( final PaymentRegisterRepository registers,
                                 final PaymentRepository payments,
                                 final SettlementRepository settlements,
                                 final ClearingRepository clearings) {
        return new PaymentFacade( new PaymentFactory( registers), payments,
                                  new ClearingFactory( settlements), clearings);
    }
}
