package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.invoice.InvoiceType.InvoiceTypeVisitor;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineFactory;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.register.RegisterRepository;
import pl.janksiegowy.backend.settlement.InvoiceSettlement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
public class InvoiceFactory {

    private final EntityRepository entities;
    private final MetricRepository metrics;
    private final PeriodRepository periods;
    private final RegisterRepository registers;
    private final InvoiceLineFactory line;

    public Invoice from( InvoiceDto source) {
        return update( source, source.getType().accept( new InvoiceTypeVisitor<Invoice>() {
            @Override public Invoice visitPayable() {
                return registers.findVatPurchaseRegisterByCode( source.getVatRegister().getCode())
                        .map( register-> new SupplierInvoice()
                                .setVatRegister( register))
                        .orElseThrow();

            }
            @Override public Invoice visitReceivable() {
                return registers.findVatSalesRegisterByCode( source.getVatRegister().getCode())
                        .map( register-> new CustomerInvoice()
                                .setVatRegister( register))
                        .orElseThrow();
            }

        }).setSettlement( new InvoiceSettlement())
                .setInvoiceId( Optional.ofNullable( source.getInvoiceId())
                .orElse( UUID.randomUUID())));
    }

    public Invoice update( InvoiceDto source, Invoice invoice) {

        Optional.ofNullable( source.getEntity()).ifPresent( entityDto->
                entities.findByEntityIdAndDate( entityDto.getEntityId(), source.getInvoiceDate())
                        .ifPresent( entity-> invoice.setEntity( entity)));

        Optional.ofNullable( source.getLineItems())
                .ifPresent( invoiceLines->update( invoiceLines, invoice));

        return invoice
                .setNumber( source.getNumber())
                .setDates( source.getInvoiceDate(), source.getIssueDate(), source.getDueDate())
                .setMetric( metrics.findByDate( source.getInvoiceDate()).orElseThrow())
                .setPeriod( periods.findMonthByDate( source.getInvoiceDate()).orElseThrow());
    }

    public Invoice update( List<InvoiceLineDto> lines, Invoice invoice) {

        return invoice.setLineItems( lines.stream().map( invoiceLineDto->
                line.from( invoiceLineDto, Optional.ofNullable( invoice.getInvoiceDate()).orElse( LocalDate.now()))
                        .setInvoice( invoice))
                .collect( Collectors.toList()));
    }

}
