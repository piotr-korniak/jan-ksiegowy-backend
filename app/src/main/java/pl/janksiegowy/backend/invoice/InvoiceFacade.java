package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.declaration.Factory_FA;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.invoice.InvoiceType.InvoiceTypeVisitor;
import pl.janksiegowy.backend.invoice.dto.InvoiceCsv;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.invoice.dto.InvoiceLineCommand;
import pl.janksiegowy.backend.invoice.dto.InvoiceRequest;
import pl.janksiegowy.backend.register.RegisterQueryRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;
import pl.janksiegowy.backend.shared.MigrationService;
import pl.janksiegowy.backend.shared.financial.PaymentMethod;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;

import java.util.NoSuchElementException;
import java.util.UUID;

@Log4j2

@AllArgsConstructor
public class InvoiceFacade implements InvoiceTypeVisitor<InvoiceDto.Proxy, InvoiceCsv> {

    private final InvoiceFactory factory;
    private final InvoiceRepository invoices;
    private final SettlementQueryRepository settlements;
    private final EntityQueryRepository entities;
    private final RegisterQueryRepository registers;
    private final MigrationService migrationService;
    private final PaymentRegisterRepository bankAccounts;

    private final DecreeFacade decrees;

    public void save( InvoiceRequest source) {
        factory.from( source);
    }

    public Invoice save( InvoiceDto source) {
        return invoices.save( factory.from( source));
    }

    public Invoice approve( Invoice invoice) {
        if( InvoiceStatus.N!= invoice.getStatus())
            decrees.book( invoice);
        return invoice;
    }

    @Transactional( transactionManager= "companyTransactionManager")
    public Invoice addItemToInvoice( UUID invoiceId, InvoiceLineCommand command) {
        return invoices.findByInvoiceId( invoiceId)
                .map(invoice-> saveWithXmlRegeneration( invoice
                        .addLine( factory.from( command, invoice.getInvoiceDate())
                                .setId( InvoiceLineId.random()))))
                .orElseThrow(()-> new NoSuchElementException( "Invoice with ID %s not found".formatted( invoiceId)));
    }

    private Invoice saveWithXmlRegeneration( Invoice invoice) {
        if (invoice instanceof SalesInvoice salesInvoice) {
            return invoices.save( invoice.setXml( generateSalesInvoiceXml( salesInvoice)));
        } else {
            return invoices.save(invoice);
        }
    }

    private String generateSalesInvoiceXml(SalesInvoice salesInvoice) {
        var x = Factory_FA.create().prepare(salesInvoice);

        if (PaymentMethod.TRANSFER == salesInvoice.getPaymentMethod()) {
            bankAccounts.findBankAccounts()
                    .forEach(bankAccount -> x.addBankAccount(bankAccount.getName(), bankAccount.getBankNumber()));
        }
        return XmlConverter.marshal(x);
    }

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadInvoices().forEach(invoice-> {
            counters[0]++;

            if( !settlements.existsByNumberAndEntityTaxNumber( invoice.getNumber(), invoice.getTaxNumber())) {
                save( entities.findContactByCountryAndTaxNumber( invoice.getCountry(), invoice.getTaxNumber())
                        .map(contact-> invoice.getType().accept( this, invoice)
                                .entity( contact)
                                .number( invoice.getNumber())
                                .amount( invoice.getAmount())
                                .issueDate( invoice.getIssuedDate())
                                .invoiceDate( invoice.getInvoicedDate())
                                .dueDate( invoice.getDueDate()))
                        .map( proxy-> invoice.isPaymentMethod()
                                ? proxy.paymentMethod( invoice.getPaymentMethod()): proxy)
                        .map( proxy -> invoice.isCorrection()
                                ? proxy.correction( invoice.getCorrection()): proxy)
                        .orElseThrow(()-> new NoSuchElementException(
                                "Not found contact with tax number: " + invoice.getTaxNumber())));
                counters[1]++;
            }
        });

        log.warn( "Invoices migration complete!");
        return "%-40s %16s".formatted("Invoices migration complete, added: ", counters[1]+ "/"+ counters[0]);
    }

    @Override public InvoiceDto.Proxy visitSalesInvoice( InvoiceCsv source) {
        return registers.findSalesRegisterByCode( source.getRegisterCode())
                .map( salesRegister-> InvoiceDto.create()
                        .registerId( salesRegister.getRegisterId())
                        .type( InvoiceType.S))
                .orElseThrow(()-> new NoSuchElementException( "Not found register code: "+ source.getRegisterCode()));
    }

    @Override public InvoiceDto.Proxy visitPurchaseInvoice( InvoiceCsv source) {
        return registers.findPurchaseRegisterByCode( source.getRegisterCode())
                .map( purchaseRegister-> InvoiceDto.create()
                        .registerId( purchaseRegister.getRegisterId())
                        .type( InvoiceType.P))
                .orElseThrow(()-> new NoSuchElementException( "Not found register code: "+ source.getRegisterCode()));
    }


}
