package pl.janksiegowy.backend.finances.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingFactory;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterFactory;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementRepository;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

@Configuration
public class PaymentConfiguration {

    @Bean
    PaymentFacade paymentFacade( final PaymentRegisterRepository registers,
                                 final PaymentRepository payments,
                                 final SettlementRepository settlements,
                                 final ClearingRepository clearings,
                                 final ClearingFactory clearing,
                                 final NumeratorFacade numerator,
                                 final PeriodFacade periods,
                                 final EntityRepository entities,
                                 final NumeratorFacade numerators,
                                 final DecreeFacade decrees) {
        return new PaymentFacade(
                new PaymentFactory( registers, numerator, periods, entities, clearing), payments,
                new ClearingFactory( settlements), clearings,
                new PaymentRegisterFactory( numerators), registers, decrees);
    }
}
