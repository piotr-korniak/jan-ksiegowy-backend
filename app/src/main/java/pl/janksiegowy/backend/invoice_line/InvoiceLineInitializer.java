package pl.janksiegowy.backend.invoice_line;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.invoice.InvoiceFacade;
import pl.janksiegowy.backend.invoice.InvoiceQueryRepository;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceMap;
import pl.janksiegowy.backend.item.ItemQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;

import java.math.BigDecimal;

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
                                            .amount( parse( fields[2], 2))
                                            .tax( parse( fields[3], 2))))
                                    .orElseThrow());
                        return invoiceDto;
                    });
        }
    }

    private BigDecimal parse(String kwota, int precyzja){

        System.err.println( "Kwota: "+ kwota);

        StringBuilder sb= new StringBuilder( kwota);
        // dostawiamy zera po przecinku, precyzja dla BigDecimal
        int n= precyzja;
        while( n--> 0)
            sb.append( '0');
        // zamieniamy przecinek na kropke
        n= kwota.lastIndexOf( ',');

        if( n>0){
            sb.replace( n, n+1, ".");
            sb.delete( n+precyzja+1, 100);
        }
        // usuwamy biale znaki
        while((n=sb.indexOf( "\u00A0"))> 0){
            sb.delete( n, n+1);
        }

        return new BigDecimal( sb.toString());
    }
}
