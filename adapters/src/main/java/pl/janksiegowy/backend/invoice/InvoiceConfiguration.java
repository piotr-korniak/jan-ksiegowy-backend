package pl.janksiegowy.backend.invoice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineFactory;
import pl.janksiegowy.backend.item.ItemRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.RegisterQueryRepository;
import pl.janksiegowy.backend.register.RegisterRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;
import pl.janksiegowy.backend.shared.MigrationService;


@Configuration
public class InvoiceConfiguration {

    @Bean
    InvoiceFacade invoiceFacade(final InvoiceRepository invoices,
                                final EntityRepository entityRepository,
                                final MetricRepository metrics,
                                final PaymentRegisterRepository payments,
                                final RegisterQueryRepository registers,
                                final RegisterRepository registerRepository,
                                final PeriodFacade periods,
                                final ItemRepository items,
                                final SettlementQueryRepository settlements,
                                final EntityQueryRepository entities,
                                final MigrationService migrationService,
                                final PaymentRegisterRepository bankAccount,
                                final DecreeFacade decreeFacade) {
        return new InvoiceFacade(
                new InvoiceFactory( entityRepository, metrics, periods, payments,
                        new InvoiceLineFactory( items), registerRepository, items),
                invoices,
                settlements,
                entities,
                registers,
                migrationService,
                bankAccount,
                decreeFacade);
    }

}
