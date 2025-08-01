package pl.janksiegowy.backend.invoice_line;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.invoice.InvoiceFacade;
import pl.janksiegowy.backend.invoice.InvoiceQueryRepository;
import pl.janksiegowy.backend.invoice.TaxedAmount;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.invoice.dto.InvoiceLineCommand;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceMap;
import pl.janksiegowy.backend.item.ItemQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.MigrationService;
import pl.janksiegowy.backend.shared.Util;

@Service
@AllArgsConstructor
public class InvoiceLineMigration {

    private final InvoiceQueryRepository invoices;
    private final InvoiceFacade facade;
    private final ItemQueryRepository items;
    private final DataLoader loader;
    private final MigrationService migrationService;

    private Invoice save( InvoiceDto invoice) {
        return facade.approve( facade.save( invoice));
    }

    public String init() {

        var total= 0;
        var added= new Object(){ int value= 0;};

        migrationService.loadInvoiceLines().forEach( invoice-> {


        });

        for( String[] fields: loader.readData( "lines.csv")) {
            if (fields[0].startsWith( "---"))
                continue;
            total++;

            invoices.findByNumber( InvoiceDto.class, fields[0])
                    .map( InvoiceMap::new)
                    .ifPresent( invoiceMap-> {
                        if( invoiceMap.getLineItems().stream()
                                .noneMatch(i-> i.getItem().getCode().equals( fields[1]))) {

                            items.findByCode( fields[1])
                                    .map(itemDto-> facade.addItemToInvoice(
                                            invoiceMap.getDocumentId(),
                                            InvoiceLineCommand.create().item( itemDto)
                                                    .amount( TaxedAmount.of( Util.toBigDecimal( fields[2], 2),
                                                            Util.toBigDecimal( fields[3], 2),
                                                            itemDto.getTaxRate()))))
                                    .orElseThrow();

                            added.value++;
                        }
                    });
        }
        return String.format( "%-40s %16s", "Lines migration complete, added: ", added.value+ "/"+ total);
    }

}
