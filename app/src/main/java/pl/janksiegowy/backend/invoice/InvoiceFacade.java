package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;

@AllArgsConstructor
public class InvoiceFacade {

    private final InvoiceFactory invoice;
    private final InvoiceRepository invoices;

    private final DecreeFacade decrees;

    public Invoice save( InvoiceDto source) {
        return invoices.save( invoice.from( source));
    }

    public Invoice approve( Invoice invoice) {
        if( InvoiceStatus.N!= invoice.getStatus())
            decrees.book( invoice);
        return invoice;
    }
}
