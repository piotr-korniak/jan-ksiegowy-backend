package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.accounting.decree.DecreeRepository;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.invoice.InvoiceType.InvoiceTypeVisitor;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineFactory;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterRepository;
import pl.janksiegowy.backend.finances.settlement.InvoiceSettlement;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;

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
    private final InvoiceRegisterRepository registers;
    private final DecreeRepository decrees;
    private final InvoiceLineFactory line;

    public Invoice from( InvoiceDto source) {

        return update( source, source.getType().accept( new InvoiceTypeVisitor<Invoice>() {

            @Override public Invoice visitSalesInvoice() {
                return registers.findSalesRegisterByCode( source.getRegister().getCode())
                        .map( register-> new SalesInvoice()
                                .setRegister( register)
                                .setSettlement( (InvoiceSettlement)
                                        new InvoiceSettlement().setKind( SettlementKind.D)))
                        .orElseThrow();
            }

            @Override public Invoice visitPurchaseInvoice() {
                return registers.findPurchaseRegisterByCode( source.getRegister().getCode())
                        .map( register-> new PurchaseInvoice()
                                .setRegister( register)
                                .setSettlement( (InvoiceSettlement)
                                        new InvoiceSettlement().setKind( SettlementKind.C)))
                        .orElseThrow();
            }


        }).setInvoiceId( Optional.ofNullable( source.getInvoiceId())
                .orElseGet(()-> UUID.randomUUID())));
    }

    public Invoice update( InvoiceDto source, Invoice invoice) {

        Optional.ofNullable( source.getEntity()).ifPresent( entityDto->
                entities.findByEntityIdAndDate( entityDto.getEntityId(), source.getInvoiceDate())
                        .ifPresent( entity-> invoice.setEntity( entity)));

        Optional.ofNullable( source.getLineItems())
                .ifPresent( invoiceLines->update( invoiceLines, invoice));

        decrees.findById( invoice.getInvoiceId())
                .ifPresent( invoice::setDecree);

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
