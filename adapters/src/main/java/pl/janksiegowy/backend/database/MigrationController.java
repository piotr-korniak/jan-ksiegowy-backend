package pl.janksiegowy.backend.database;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.accounting.account.AccountFacade;
import pl.janksiegowy.backend.accounting.account.AccountInitializer;
import pl.janksiegowy.backend.accounting.account.AccountQueryRepository;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.accounting.template.TemplateFacade;
import pl.janksiegowy.backend.accounting.template.TemplateInitializer;
import pl.janksiegowy.backend.accounting.template.TemplateQueryRepository;
import pl.janksiegowy.backend.entity.EntityFacade;
import pl.janksiegowy.backend.entity.EntityInitializer;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.charge.ChargeFacade;
import pl.janksiegowy.backend.finances.charge.ChargeInitializer;
import pl.janksiegowy.backend.finances.clearing.ClearingFactory;
import pl.janksiegowy.backend.finances.note.NoteFacade;
import pl.janksiegowy.backend.finances.note.NoteInitializer;
import pl.janksiegowy.backend.finances.payment.PaymentQueryRepository;
import pl.janksiegowy.backend.finances.settlement.*;
import pl.janksiegowy.backend.finances.share.ShareFacade;
import pl.janksiegowy.backend.finances.share.ShareInitializer;
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
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;
import pl.janksiegowy.backend.finances.payment.PaymentFacade;
import pl.janksiegowy.backend.finances.payment.PaymentInitializer;
import pl.janksiegowy.backend.period.*;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterQueryRepository;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterQueryRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterQueryRepository;
import pl.janksiegowy.backend.register.RegisterInitializer;
import pl.janksiegowy.backend.salary.ContractFacade;
import pl.janksiegowy.backend.salary.ContractInitializer;
import pl.janksiegowy.backend.salary.ContractQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.numerator.*;
import pl.janksiegowy.backend.subdomain.TenantController;

import java.time.LocalDate;

@Log4j2

@TenantController
@RequestMapping( "/v2/migrate")
public class MigrationController extends MigrationConfiguration {

    private final EntityInitializer entities;
    private final MetricInitializer metrics;
    private final RegisterInitializer registers;
    private final PeriodInitializer periods;
    private final InvoiceInitializer invoices;
    private final ItemInitializer items;
    private final InvoiceLineInitializer lines;
    private final PaymentInitializer payments;
    private final NumeratorInitializer numerators;
    private final TemplateInitializer templates;
    private final AccountInitializer accounts;

    private final ContractInitializer contracts;
    private final SettlementInitializer settlements;

    private final NoteInitializer notices;
    private final ChargeInitializer charges;
    private final ShareInitializer shares;

    private final PeriodDto[] initialPeriods= {
            PeriodDto.create().type( PeriodType.A)
                .begin( LocalDate.of( 2017, 1, 1))
                .end( LocalDate.of( 2017, 12, 31)),
            PeriodDto.create().type( PeriodType.A)
                    .begin( LocalDate.of( 2018, 1, 1))
                    .end( LocalDate.of( 2018, 12, 31)),
            PeriodDto.create().type( PeriodType.A)
                    .begin( LocalDate.of( 2021, 1, 1))
                    .end( LocalDate.of( 2021, 12, 31)),
            PeriodDto.create().type( PeriodType.A)
                    .begin( LocalDate.of( 2022, 1, 1))
                    .end( LocalDate.of( 2022, 12, 31)),
            PeriodDto.create().type( PeriodType.A)
                .begin( LocalDate.of( 2023, 1, 1))
                .end( LocalDate.of( 2023, 12, 31)),
            PeriodDto.create().type( PeriodType.A)
                .begin( LocalDate.of( 2024, 1, 1))
                .end( LocalDate.of( 2024, 12, 31))
    };

    public MigrationController(final EntityQueryRepository entities,
                               final EntityFacade entity,
                               final MetricRepository metrics,
                               final InvoiceRegisterQueryRepository invoiceRegisters,
                               final PaymentRegisterQueryRepository paymentRegisters,
                               final PaymentQueryRepository payments,
                               final AccountingRegisterQueryRepository accountingRegisters,
                               final InvoiceQueryRepository invoices,
                               final InvoiceFacade invoice,
                               final PeriodFacade period,
                               final PeriodQueryRepository periods,
                               final SettlementQueryRepository settlements,
                               final ItemQueryRepository items,
                               final ItemFacade item,
                               final ClearingQueryRepository clearing,
                               final PaymentFacade payment,
                               final NumeratorQueryRepository numerators,
                               final NumeratorFacade numerator,
                               final TemplateQueryRepository templates,
                               final TemplateFacade template,
                               final DecreeFacade decree,
                               final AccountFacade account,
                               final AccountQueryRepository accounts,
                               final ContractFacade contract,
                               final ContractQueryRepository contracts,
                               final SettlementFacade settlement,
                               final SettlementRepository settlementRepository,
                               final EntityRepository entityRepository,
                               final NoteFacade notice,
                               final ChargeFacade charge,
                               final ShareFacade share,

                                final DataLoader loader) {

        this.metrics = new MetricInitializer( new MetricFactory(), metrics, loader);
        this.entities= new EntityInitializer( entities, entity, loader);
        this.registers= new RegisterInitializer( invoiceRegisters, paymentRegisters, accountingRegisters,
                                                   decree, invoice, payment, loader);
        this.periods= new PeriodInitializer( period);
        this.invoices= new InvoiceInitializer( settlements, invoiceRegisters,
                                                periods, entities, period, invoice, loader);
        this.items= new ItemInitializer( items, item, loader);
        this.lines= new InvoiceLineInitializer( invoices, invoice, items, loader);
        this.payments= new PaymentInitializer( clearing, settlements, paymentRegisters, payment, settlement,
                new SettlementFactory( entityRepository, new ClearingFactory( settlementRepository)),
                payments, period, loader);
        this.numerators= new NumeratorInitializer( numerators, numerator);
        this.templates= new TemplateInitializer( templates, template);
        this.accounts= new AccountInitializer( account, accounts);
        this.contracts= new ContractInitializer( contracts, entities, contract);
        this.settlements= new SettlementInitializer( settlements, entities, settlement, decree, loader);
        this.notices= new NoteInitializer( settlements, entities, notice, loader);
        this.charges = new ChargeInitializer( settlements, entities, charge, loader);
        this.shares= new ShareInitializer( settlements, entities, share, loader);
    }

    @PostMapping
    public ResponseEntity migrate() {

        var response= new StringBuilder();

        log.warn( "Migration start...");

        numerators.init( getInitialNumerators());
        log.warn( "Numerators migration complete!");

        registers.initInvoiceRegisters();
        registers.initPaymentRegisters();
        registers.initAccountingRegisters( getAccountingRegister());
        log.warn( "Registers migration complete!");


        accounts.init( getInitialAccount());
        log.warn( "Accounts migration complete!");

        templates.init( getInitialTemplates());
        log.warn( "Templates migration complete!");

        metrics.init();
        log.warn( "Metrics migration complete!");

        entities.init();
        log.warn( "Entities migration complete!");

        periods.init( initialPeriods);
        log.warn( "Periods migration complete!");

        items.init();
        log.warn( "Items migration complete!");

        response.append( invoices.init());
        log.warn( "Invoices migration complete!");

        lines.init();
        log.warn( "Invoices Lines migration complete!");

        response.append( contracts.init( getInitialContracts()));
        log.warn( "Contracts migration complete!");
/*
        response.append( settlements.init());
        log.warn( "Settlements migration complete!");
*/

        response.append( notices.init());

        response.append( charges.init());

        response.append( shares.init());

        payments.init();
        log.warn( "Payments migration complete!");

        return ResponseEntity.ok( response.toString());
    }
}
