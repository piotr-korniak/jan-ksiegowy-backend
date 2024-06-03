package pl.janksiegowy.backend.invoice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.accounting.decree.DecreeRepository;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineFactory;
import pl.janksiegowy.backend.item.ItemRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterFactory;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterQueryRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;


@Configuration
public class InvoiceConfiguration {

    @Bean
    InvoiceFacade invoiceFacade(final InvoiceRepository invoices,
                                final EntityRepository entities,
                                final MetricRepository metrics,
                                final InvoiceRegisterFactory register,
                                final InvoiceRegisterRepository registers,
                                final PaymentRegisterRepository payments,
                                 final PeriodFacade periods,
                                final ItemRepository items,
                                final DecreeFacade decree) {
        return new InvoiceFacade(
                new InvoiceFactory( entities, metrics, periods, registers, payments, new InvoiceLineFactory( items)),
                invoices, register, registers, decree);
    }

}
