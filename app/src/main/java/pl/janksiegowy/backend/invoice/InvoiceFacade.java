package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.register.Register;
import pl.janksiegowy.backend.register.RegisterFactory;
import pl.janksiegowy.backend.register.dto.VatRegisterDto;
import pl.janksiegowy.backend.register.RegisterRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class InvoiceFacade {

    private final InvoiceFactory invoice;
    private final InvoiceRepository invoices;

    private final RegisterFactory register;
    private final RegisterRepository registers;

    public Invoice save( InvoiceDto source) {
        return invoices.save( invoice.from( source));
    }

    public Register save( VatRegisterDto source) {
        return registers.save( Optional.ofNullable( source.getRegisterId())
                .map( uuid -> register.update( source))
                .orElse( register.from( source)));
    }

}
