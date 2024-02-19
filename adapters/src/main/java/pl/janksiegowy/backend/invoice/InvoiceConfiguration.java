package pl.janksiegowy.backend.invoice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineFactory;
import pl.janksiegowy.backend.item.ItemRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.register.InvoiceRegisterFactory;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterRepository;


@Configuration
public class InvoiceConfiguration {

    @Bean
    InvoiceFacade invoiceFacade( final InvoiceRepository invoices,
                                 final EntityRepository entities,
                                 final MetricRepository metrics,
                                 final InvoiceRegisterFactory register,
                                 final InvoiceRegisterRepository registers,
                                 final PeriodRepository periods,
                                 final ItemRepository items) {
        return new InvoiceFacade(
                new InvoiceFactory( entities, metrics, periods, registers,
                        new InvoiceLineFactory( items)),
                invoices, register, registers);
    }

}
