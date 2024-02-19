package pl.janksiegowy.backend.database;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.entity.EntityFacade;
import pl.janksiegowy.backend.entity.EntityInitializer;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.invoice.InvoiceFacade;
import pl.janksiegowy.backend.invoice.InvoiceInitializer;
import pl.janksiegowy.backend.invoice.InvoiceQueryRepository;
import pl.janksiegowy.backend.invoice_line.InvoiceLineInitializer;
import pl.janksiegowy.backend.item.ItemFacade;
import pl.janksiegowy.backend.item.ItemInitializer;
import pl.janksiegowy.backend.item.ItemQueryRepository;
import pl.janksiegowy.backend.metric.MetricFactory;
import pl.janksiegowy.backend.metric.MetricInitializer;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.payment.ClearingQueryRepository;
import pl.janksiegowy.backend.payment.PaymentFacade;
import pl.janksiegowy.backend.payment.PaymentInitializer;
import pl.janksiegowy.backend.period.*;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.register.InvoiceRegisterQueryRepository;
import pl.janksiegowy.backend.register.PaymentRegisterQueryRepository;
import pl.janksiegowy.backend.register.RegisterInitializer;
import pl.janksiegowy.backend.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.subdomain.TenantController;

import java.time.LocalDate;

@Log4j2

@TenantController
@RequestMapping( "/v2/migrate")
public class MigrationController {

    private final EntityInitializer entities;
    private final MetricInitializer metrics;
    private final RegisterInitializer registers;
    private final PeriodInitializer periods;
    private final InvoiceInitializer invoices;
    private final ItemInitializer items;
    private final InvoiceLineInitializer lines;
    private final PaymentInitializer payments;

    private final PeriodDto[] initialPeriods= {
            PeriodDto.create().type( PeriodType.A)
                .begin( LocalDate.of( 2017, 1, 1))
                .end( LocalDate.of( 2017, 12, 31)),
            PeriodDto.create().type( PeriodType.A)
                .begin( LocalDate.of( 2023, 1, 1))
                .end( LocalDate.of( 2023, 12, 31)),
            PeriodDto.create().type( PeriodType.A)
                .begin( LocalDate.of( 2024, 1, 1))
                .end( LocalDate.of( 2024, 12, 31))
    };

    public MigrationController( final EntityQueryRepository entities,
                                final EntityFacade entity,
                                final MetricRepository metrics,
                                final InvoiceRegisterQueryRepository invoiceRegisters,
                                final PaymentRegisterQueryRepository paymentRegisters,
                                final InvoiceQueryRepository invoices,
                                final InvoiceFacade invoice,
                                final PeriodFacade period,
                                final PeriodQueryRepository periods,
                                final SettlementQueryRepository settlements,
                                final ItemQueryRepository items,
                                final ItemFacade item,
                                final ClearingQueryRepository clearing,
                                final PaymentFacade payments,
                                final DataLoader loader) {

        this.metrics = new MetricInitializer( new MetricFactory(), metrics, loader);
        this.entities= new EntityInitializer( entities, entity, loader);
        this.registers= new RegisterInitializer( invoiceRegisters, paymentRegisters, invoice, loader);
        this.periods= new PeriodInitializer( period);
        this.invoices= new InvoiceInitializer( settlements, invoiceRegisters,
                                                periods, entities, period, invoice, loader);
        this.items= new ItemInitializer( items, item, loader);
        this.lines= new InvoiceLineInitializer( invoices, invoice, items, loader);
        this.payments= new PaymentInitializer( clearing, settlements, paymentRegisters, payments, loader);
    }

    @PostMapping
    public ResponseEntity migrate() {

        //TenantContext.setCurrentTenant( "eleftheria", "pl5862321911");
        log.warn( "Migration start...");
/*
        metrics.init();
        log.warn( "Metrics migration complete!");

        entities.init();
        log.warn( "Entities migration complete!");

        registers.init();
        log.warn( "Registers migration complete!");

        periods.init( initialPeriods);
        log.warn( "Periods migration complete!");

        items.init();
        log.warn( "Items migration complete!");

        invoices.init();
        log.warn( "Invoices migration complete!");

        lines.init();
        log.warn( "Invoices Lines migration complete!");
*/
        payments.init();

        return ResponseEntity.ok().build();
    }
}
