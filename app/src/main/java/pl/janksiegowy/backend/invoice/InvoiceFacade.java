package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.register.Register;
import pl.janksiegowy.backend.register.invoice.InvoiceRegister;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterFactory;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterRepository;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.util.Optional;

@AllArgsConstructor
public class InvoiceFacade {

    private final InvoiceFactory invoice;
    private final InvoiceRepository invoices;

    private final InvoiceRegisterFactory register;
    private final InvoiceRegisterRepository registers;

    private final DecreeFacade decrees;

    public Invoice save( InvoiceDto source) {
        return invoices.save( invoice.from( source));
    }

    public InvoiceRegister save( RegisterDto source) {
        return registers.save( Optional.ofNullable( source.getRegisterId())
                .map( uuid -> register.update( source))
                .orElse( register.from( source)));
    }

    public Invoice approve( Invoice invoice) {
        if( InvoiceStatus.N!= invoice.getStatus())
            decrees.book( invoice);
        return invoice;
    }
}
