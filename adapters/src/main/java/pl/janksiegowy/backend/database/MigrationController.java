package pl.janksiegowy.backend.database;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.janksiegowy.backend.accounting.account.AccountFacade;
import pl.janksiegowy.backend.accounting.template.TemplateFacade;
import pl.janksiegowy.backend.accounting.template.TemplateMiagrationService;
import pl.janksiegowy.backend.entity.EntityMigrationService;
import pl.janksiegowy.backend.finances.charge.ChargeMigrationService;
import pl.janksiegowy.backend.finances.notice.NoticeFacade;
import pl.janksiegowy.backend.finances.share.ShareMigrationService;
import pl.janksiegowy.backend.invoice.InvoiceMigrationService;
import pl.janksiegowy.backend.invoice_line.InvoiceLineMigration;
import pl.janksiegowy.backend.item.ItemFacade;
import pl.janksiegowy.backend.item.ItemInitializer;
import pl.janksiegowy.backend.item.ItemQueryRepository;
import pl.janksiegowy.backend.metric.MetricFacade;
import pl.janksiegowy.backend.finances.payment.PaymentMigrationService;
import pl.janksiegowy.backend.period.*;
import pl.janksiegowy.backend.register.RegisterMigrationService;
import pl.janksiegowy.backend.report.ReportFacade;
import pl.janksiegowy.backend.contract.ContractFacade;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.numerator.*;
import pl.janksiegowy.backend.subdomain.TenantController;

import java.util.List;

@Log4j2

@TenantController
public class MigrationController extends MigrationConfiguration {

    private final ItemInitializer items;

    private final ReportFacade reportFacade;
    private final NoticeFacade noticeFacade;
    private final ContractFacade contractFacade;
    private final MetricFacade metricFacade;
    private final DataLoader dataLoader;
    private final AccountFacade accountFacade;
    private final TemplateFacade templateFacade;
    private final PeriodFacade periodFacade;
    private final NumeratorFacade numeratorFacade;

    public MigrationController(final PeriodFacade period,
                               final ItemQueryRepository items,
                               final ItemFacade item,
                               final NumeratorQueryRepository numerators,
                               final NumeratorFacade numerator,
                               final NoticeFacade noticeFacade,
                               final InvoiceMigrationService migrationService,
                               final MigrationExecutor migrationExecutor,
                               final ShareMigrationService shareMigration,
                               final InvoiceLineMigration invoiceLinesMigration,
                               final ChargeMigrationService chargesMigration,
                               final RegisterMigrationService registersMigration,
                               final PaymentMigrationService paymentsMigration,
                               final TemplateMiagrationService templatesMigration,
                               final EntityMigrationService entityMigrationService,
                               final ReportFacade reportFacade,
                               final ContractFacade contractFacade,
                               final MetricFacade metricFacade,
                               final DataLoader dataLoader,
                               final AccountFacade accountFacade,
                               final TemplateFacade templateFacade,
                               final PeriodFacade periodFacade,
                               final NumeratorFacade numeratorFacade) {
        this.invoiceMigration = migrationService;
        this.migrationExecutor= migrationExecutor;

        this.items= new ItemInitializer( items, item, dataLoader);
        this.shareMigration= shareMigration;
        this.invoiceLinesMigration= invoiceLinesMigration;
        this.chargesMigration= chargesMigration;
        this.registersMigration= registersMigration;
        this.paymentsMigration= paymentsMigration;
        this.templatesMigration= templatesMigration;
        this.entityMigrationService= entityMigrationService;
        this.reportFacade = reportFacade;
        this.noticeFacade= noticeFacade;
        this.contractFacade= contractFacade;
        this.metricFacade= metricFacade;
        this.dataLoader= dataLoader;
        this.accountFacade = accountFacade;
        this.templateFacade= templateFacade;
        this.periodFacade= periodFacade;
        this.numeratorFacade= numeratorFacade;
    }

    private final MigrationExecutor migrationExecutor;
    private final InvoiceMigrationService invoiceMigration;
    private final ShareMigrationService shareMigration;
    private final InvoiceLineMigration invoiceLinesMigration;
    private final ChargeMigrationService chargesMigration;
    private final RegisterMigrationService registersMigration;
    private final PaymentMigrationService paymentsMigration;
    private final TemplateMiagrationService templatesMigration;
    private final EntityMigrationService entityMigrationService;


    @PostMapping( "/v2/migrate/invoice")
    public ResponseEntity<String> invoiceMigrate() {
        log.warn( "Invoices migration complete!");
        return ResponseEntity.ok( invoiceMigration.init());
    }

    @PostMapping( "/v2/migrate/line")
    public ResponseEntity<String> lineMigrate() {
        log.warn("Invoices Lines migration complete!");
        return ResponseEntity.ok( invoiceLinesMigration.init());
    }

    @PostMapping( "/v2/migrate/share")
    public ResponseEntity<String> shareMigrate() {
        log.warn( "Shares migration complete!");
        return ResponseEntity.ok( shareMigration.init());
    }

    @PostMapping( "/v2/migrate/contract")
    public ResponseEntity<String> contractMigrate() {
        return ResponseEntity.ok( contractFacade.migrate());
    }

    @PostMapping( "/v2/migrate/charge")
    public ResponseEntity<String> chargeMigrate() {
        log.warn( "Charges migration complete!");
        return ResponseEntity.ok( chargesMigration.init());
    }

    @PostMapping( "/v2/migrate/notice")
    public ResponseEntity<String> noticeMigrate() {
        return ResponseEntity.ok( noticeFacade.migrate());
    }
    //response.append( notices.init());

    @PostMapping( "/v2/migrate/entity")
    public ResponseEntity<String> entityEntity() {
        return ResponseEntity.ok( entityMigrationService.init());
    }

    @PostMapping( "/v2/migrate/register")
    public ResponseEntity<String> registerMigrate() {
        //    registers.initInvoiceRegisters();
        //registers.initAccountingRegisters( getAccountingRegister());
        log.warn( "Registers configuration complete!");
        return ResponseEntity.ok( registersMigration.initPaymentRegisters());
    }

    @PostMapping( "/v2/migrate/payment")
    public ResponseEntity<String> paymentMigrate() {
        return ResponseEntity.ok( paymentsMigration.init());
    }

    @PostMapping( "/v2/migrate/template")
    public ResponseEntity<String> templateMigrate() {
        return ResponseEntity.ok( templateFacade.migrate());
    }

    @PostMapping( "/v2/migrate/report")
    public ResponseEntity<String> reportMigrate() {
        return ResponseEntity.ok( reportFacade.migrate());
    }

    @PostMapping( "/v2/migrate/metric")
    public ResponseEntity<String> metricMigrate() {
        return ResponseEntity.ok( metricFacade.migrate());
    }

    @PostMapping( "/v2/migrate/account")
    public ResponseEntity<String> accountMigrate() {
        return ResponseEntity.ok( accountFacade.migrate());
    }

    @PostMapping( "/v2/migrate/period")
    public ResponseEntity<String> accountPeriod() {
        return ResponseEntity.ok( periodFacade.migrate());
    }

    @PostMapping( "/v2/migrate/numerator")
    public ResponseEntity<String> accountNumerator() {
        return ResponseEntity.ok( numeratorFacade.migrate());
    }

    @PostMapping("/v2/update")
    public ResponseEntity<List<String>> migrate( @RequestParam String from) {
        return ResponseEntity.ok( dataLoader
                .loadYml( from, MigrationDto.class).stream()
                .map( migrationExecutor::executeMigration)
                .toList());
    }

/*
        items.init();
        log.warn( "Items migration complete!");
*/
}
