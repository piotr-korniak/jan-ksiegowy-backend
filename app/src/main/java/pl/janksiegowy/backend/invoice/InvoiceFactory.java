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
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterRepository;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;
import pl.janksiegowy.backend.statement.Factory_FA;
import pl.janksiegowy.backend.statement.Factory_FA_2_v1_0e;

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
    private final PeriodFacade periods;
    private final InvoiceRegisterRepository registers;
    private final DecreeRepository decrees;
    private final InvoiceLineFactory line;

    public Invoice from( InvoiceDto source) {

        return source.getType().accept( new InvoiceTypeVisitor<Invoice>() {

            @Override public Invoice visitSalesInvoice() {
                var invoice= update( source, registers.findSalesRegisterByCode( source.getRegister().getCode())
                        .map( register-> new SalesInvoice()
                                .setRegister( register)
                           //     .setSettlement( (InvoiceSettlement)
                           //             new InvoiceSettlement().setKind( SettlementKind.D)))
                        ).orElseThrow());
                var x= Factory_FA.create().prepare( invoice);
                var xml= XmlConverter.marshal( x);
                System.err.println( "XML: \n"+ xml.toString());
                System.exit( 0);
                return invoice;
            }

            @Override public Invoice visitPurchaseInvoice() {
                return update( source, registers.findPurchaseRegisterByCode( source.getRegister().getCode())
                        .map( register-> new PurchaseInvoice()
                                .setRegister( register)
                         //       .setSettlement( (InvoiceSettlement)
                         //               new InvoiceSettlement().setKind( SettlementKind.C)))
                        ).orElseThrow());
            }


        });
    }

    public Invoice update( InvoiceDto source, Invoice invoice) {

        Optional.ofNullable( source.getEntity()).ifPresent( entityDto->
                entities.findByEntityIdAndDate( entityDto.getEntityId(), source.getInvoiceDate())
                        .ifPresent( entity-> invoice.setEntity( entity)));

        Optional.ofNullable( source.getLineItems())
                .ifPresent( invoiceLines->update( invoiceLines, invoice));

        //decrees.findById( invoice.getDocumentId())
        //        .ifPresent( invoice::setDecree);

        return (Invoice)invoice
                .setMetric( metrics.findByDate( source.getInvoiceDate()).orElseThrow())
                .setInvoiceDate( source.getInvoiceDate())
                .setInvoicePeriodId( periods.findMonthPeriodOrAdd( source.getInvoiceDate()).getId())
                .setPeriod( periods.findMonthPeriodOrAdd( source.getDate()))
                .setDate( source.getDate())
                .setDue( source.getDue())
                .setNumber( source.getNumber())
                .setDocumentId( Optional.ofNullable( source.getDocumentId())
                        .orElseGet( UUID::randomUUID));
    }

    public Invoice update( List<InvoiceLineDto> lines, Invoice invoice) {

        return invoice.setLineItems( lines.stream().map( invoiceLineDto->
                line.from( invoiceLineDto, Optional.ofNullable( invoice.getInvoiceDate()).orElse( LocalDate.now()))
                        .setInvoice( invoice))
                .collect( Collectors.toList()));
    }

}
