package pl.janksiegowy.backend.invoice_line.dto;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.invoice.TaxMethodVisitorImpl;
import pl.janksiegowy.backend.invoice_line.InvoiceLine;
import pl.janksiegowy.backend.item.ItemRepository;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
public class InvoiceLineFactory {

    private final ItemRepository items;



    public InvoiceLine from( InvoiceLineDto invoiceLineDto, LocalDate date) {

        var line= new InvoiceLine();
        /*        .setId( invoiceLineDto.getId())
                .setAmount( invoiceLineDto.getAmount())
                .setTax( invoiceLineDto.getTax())
                .setTaxRate( invoiceLineDto.getTaxRate());*/

        Optional.ofNullable( invoiceLineDto.getItem())
                .map( itemDto-> items.findItemByItemIdAndDate( itemDto.getItemId(), date)
                        .map( item-> item.getTaxMetod()
                                .accept( new TaxMethodVisitorImpl( line))
                                .setItem( item))
                        .orElseThrow( NoSuchElementException::new)
                )
                .orElseThrow( () -> new NoSuchElementException(
                                        "Not found Item: "+ invoiceLineDto.getItem().getCode()));

        return line;
    }
}
