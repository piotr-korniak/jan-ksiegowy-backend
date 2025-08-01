package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.declaration.Factory_FA;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.invoice.InvoiceType.InvoiceTypeVisitor;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.invoice.dto.InvoiceLineCommand;
import pl.janksiegowy.backend.invoice.dto.InvoiceRequest;
import pl.janksiegowy.backend.invoice_line.InvoiceLine;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineFactory;
import pl.janksiegowy.backend.item.ItemRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.Register;
import pl.janksiegowy.backend.register.RegisterRepository;
import pl.janksiegowy.backend.register.RegisterType;
import pl.janksiegowy.backend.register.invoice.PurchaseRegister;
import pl.janksiegowy.backend.register.invoice.SalesRegister;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;
import pl.janksiegowy.backend.shared.financial.PaymentMethod;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
public class InvoiceFactory implements
        InvoiceTypeVisitor<Invoice, InvoiceDto>,
        RegisterType.RegisterTypeVisitor<Invoice, Register> {

    private final EntityRepository entities;
    private final MetricRepository metrics;
    private final PeriodFacade periods;
    private final PaymentRegisterRepository bankAccounts;
    private final InvoiceLineFactory line;
    private final RegisterRepository registerRepository;
    private final ItemRepository items;

    @Override
    public Invoice visitSalesRegister( Register register) {
        return new SalesInvoice()
                .setRegister( (SalesRegister)register)
                ;
    }

    @Override
    public Invoice visitPurchaseRegister( Register register) {
        return new PurchaseInvoice().setRegister( (PurchaseRegister)register);
    }

    public Invoice from( InvoiceRequest source) {
        return registerRepository.findById( source.getRegisterId())
            .map( register-> register.getType().accept( this, register))
            .orElseThrow(()-> new IllegalArgumentException( "Not found register code: "+ source.getRegisterId()));
    }

    public Invoice from( InvoiceDto source) {
        return source.getType().accept( this, source);
    }

    @Override public Invoice visitSalesInvoice( InvoiceDto source) {
        var invoice= update( source, registerRepository.findSalesRegisterByRegisterId( source.getRegisterRegisterId())
                .map( register-> new SalesInvoice()
                        .setRegister( register))
                .orElseThrow());

        var x= Factory_FA.create().prepare( (SalesInvoice) invoice);
        if( PaymentMethod.TRANSFER== source.getPaymentMethod()) {
            bankAccounts.findBankAccounts().forEach( bankAccount-> {
                x.addBankAccount( bankAccount.getName(), bankAccount.getBankNumber());
            });
        }
        return invoice.setXml( XmlConverter.marshal( x));
    }

    @Override public Invoice visitPurchaseInvoice( InvoiceDto source) {
        return update( source, registerRepository.findPurchaseRegisterById( source.getRegisterRegisterId())
                .map( register-> new PurchaseInvoice()
                                .setRegister( register))
                .orElseThrow());
    }

    public Invoice update( InvoiceDto source, Invoice invoice) {

        Optional.ofNullable( source.getEntity()).ifPresent( entityDto->
                entities.findByEntityIdAndDate( entityDto.getEntityId(), source.getInvoiceDate())
                        .ifPresent( invoice::setEntity));

        Optional.ofNullable( source.getLineItems())
                .ifPresent( invoiceLines->update( invoiceLines, invoice, source.getInvoiceDate()));

        if( source.getPaymentMethod()!= null)
            invoice.setPaymentMethod( source.getPaymentMethod());

        if( source.getCorrection()!= null)
            invoice.setCorrection( source.getCorrection());

        //decrees.findById( invoice.getDocumentId())
        //        .ifPresent( invoice::setDecree);

        return ((Invoice)invoice
                .setMetric( metrics.findByDate( source.getInvoiceDate()).orElseThrow())
                .setInvoiceDate( source.getInvoiceDate())
                //.setInvoicePeriodId( source.getInvoicePeriod().getId())
                .setIssueDate( source.getIssueDate())
                .setPeriod( periods.findMonthPeriodOrAdd( source.getIssueDate()))
                .setDueDate( source.getDueDate())
                .setNumber( source.getNumber())
                .setAmount( source.getAmount())
                .setDocumentId( Optional.ofNullable( source.getDocumentId())
                        .orElseGet( UUID::randomUUID))
        ).setStatus( invoice.isValidated()? InvoiceStatus.V: InvoiceStatus.N);
    }

    public Invoice update( List<InvoiceLineDto> lines, Invoice invoice, LocalDate invoiceDate) {

        return invoice.setLineItems( lines.stream().map( invoiceLineDto->
                        line.from( invoiceLineDto, Optional.ofNullable( invoiceDate).orElse( LocalDate.now()))
                                .setInvoice( invoice))
                .collect( Collectors.toList()));
    }

    public InvoiceLine from( InvoiceLineCommand command, LocalDate date) {

        return Optional.ofNullable( command.getItem())
                .map( itemDto-> items.findByItemIdAndDate( itemDto.getItemId(), date)
                        .map( item-> item.getTaxMetod()
                                .accept( new TaxMethodVisitorImpl( new InvoiceLine()
                                        .setId( command.getInvoiceLineId())
                                        .setAmount( command.getAmount())))
                                .setItem( item))
                        .orElseThrow(()-> new NoSuchElementException( "Not found Item: "+ command.getItem().getCode())))
                .orElseThrow(()-> new IllegalArgumentException( "Item cannot be null"));
    }




}

















