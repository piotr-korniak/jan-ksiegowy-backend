package pl.janksiegowy.backend.finances.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingFactory;
import pl.janksiegowy.backend.finances.payment.PaymentFacade;
import pl.janksiegowy.backend.finances.payment.PaymentFactory;
import pl.janksiegowy.backend.finances.payment.PaymentRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementRepository;
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
                                 final NumeratorFacade numerator,
                                 final PeriodRepository periods,
                                 final EntityRepository entities) {
        return new PaymentFacade( new PaymentFactory( registers, numerator, periods, entities),
                payments, new ClearingFactory( settlements), clearings);
    }
}
