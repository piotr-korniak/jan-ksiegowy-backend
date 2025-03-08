package pl.janksiegowy.backend.invoice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineFactory;
import pl.janksiegowy.backend.item.ItemRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.RegisterRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;


@Configuration
public class InvoiceConfiguration {

    @Bean
    InvoiceFacade invoiceFacade( final InvoiceRepository invoices,
                                 final EntityRepository entities,
                                 final MetricRepository metrics,
                                 final PaymentRegisterRepository payments,
                                 final RegisterRepository registerRepository,
                                 final PeriodFacade periods,
                                 final ItemRepository items,
                                 final DecreeFacade decree) {
        return new InvoiceFacade(
                new InvoiceFactory( entities, metrics, periods, payments,
                        new InvoiceLineFactory( items), registerRepository),
                invoices, decree);
    }

}
