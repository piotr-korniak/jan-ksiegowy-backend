package pl.janksiegowy.backend.invoice_line;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.invoice.InvoiceFacade;
import pl.janksiegowy.backend.invoice.InvoiceQueryRepository;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceMap;
import pl.janksiegowy.backend.item.ItemQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;

@AllArgsConstructor
public class InvoiceLineInitializer {

    private final InvoiceQueryRepository invoices;
    private final InvoiceFacade invoice;
    private final ItemQueryRepository items;
    private final DataLoader loader;

    public void init() {
        for( String[] fields: loader.readData( "lines.txt")) {
            if (fields[0].startsWith( "---"))
                continue;

            invoices.findBySettlementNumber( InvoiceDto.class, fields[0])
                    .map( InvoiceMap::new)
                    .map( invoiceDto-> {
                        if( !invoiceDto.getLineItems().stream()
                                .anyMatch( i-> i.getItem().getCode().equals( fields[1])))
                            invoice.save( items.findByCode( fields[1])
                                    .map( itemDto-> invoiceDto.add( InvoiceLineDto.create()
                                            .item( itemDto)
                                            .taxRate( itemDto.getTaxRate())
                                            .amount( Util.toBigDecimal( fields[2], 2))
                                            .tax( Util.toBigDecimal( fields[3], 2))))
                                    .orElseThrow());
                        return invoiceDto;
                    });
        }
    }

}
