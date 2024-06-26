package pl.janksiegowy.backend.invoice_line.dto;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.shared.financial.TaxMethod.TaxMethodVisitor;
import pl.janksiegowy.backend.invoice_line.InvoiceLine;
import pl.janksiegowy.backend.item.ItemRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class InvoiceLineFactory {

    private final ItemRepository items;

    public InvoiceLine from( InvoiceLineDto invoiceLineDto, LocalDate date) {

        var line= new InvoiceLine()
                .setId( Optional.ofNullable( invoiceLineDto.getId()).orElseGet( UUID::randomUUID))
                .setAmount( invoiceLineDto.getAmount())
                .setTax( invoiceLineDto.getTax())
                .setTaxRate( invoiceLineDto.getTaxRate());

        Optional.ofNullable( invoiceLineDto.getItem())
                .map( itemDto-> items.findByItemIdAndDate( itemDto.getItemId(), date)
                        .map( item-> item.getTaxMetod().accept( new TaxMethodVisitor<InvoiceLine>() {
                            // fixme - tax control

                            @Override public InvoiceLine visitNL() {
                                return line.setBase( line.getAmount())
                                        .setCit( line.getAmount())
                                        .setVat( line.getTax());
                            }
                            @Override public InvoiceLine visitNC() {
                                return line.setBase( line.getAmount())
                                        .setCit( BigDecimal.ZERO)
                                        .setVat( line.getTax());
                            }

                            @Override public InvoiceLine visitVC() {
                                return line.setBase( BigDecimal.ZERO)
                                        .setCit( line.getAmount().add( line.getTax()))
                                        .setVat( BigDecimal.ZERO);
                            }

                            @Override public InvoiceLine visitV5() {
                                var vat= line.getTax()
                                        .multiply( new BigDecimal("0.5"))
                                        .setScale( 2, RoundingMode.HALF_DOWN);

                                return line.setBase( line.getAmount()
                                                .multiply( new BigDecimal("0.5"))
                                                .setScale( 2, RoundingMode.HALF_DOWN))
                                        .setVat( vat)
                                        .setCit( line.getAmount().add( line.getTax().subtract( vat)));
                            }
                            @Override public InvoiceLine visitC7() {
                                var vat= line.getTax()
                                        .multiply( new BigDecimal("0.5"))
                                        .setScale( 2, RoundingMode.HALF_DOWN);

                                return line.setBase( line.getAmount()
                                                .multiply( new BigDecimal("0.5"))
                                                .setScale( 2, RoundingMode.HALF_DOWN))
                                        .setVat( vat)
                                        .setCit( line.getAmount().add( line.getTax().subtract( vat))
                                                .multiply( new BigDecimal("0.75"))
                                                .setScale( 2, RoundingMode.HALF_DOWN));
                            }

                        }).setItem( item)))
                .orElseThrow( () -> new NoSuchElementException(
                                        "Not found Item: "+ invoiceLineDto.getItem().getCode()));

        return line;
    }
}
