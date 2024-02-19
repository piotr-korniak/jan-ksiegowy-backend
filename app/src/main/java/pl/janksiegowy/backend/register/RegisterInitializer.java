package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.invoice.InvoiceFacade;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterType;
import pl.janksiegowy.backend.shared.DataLoader;

@AllArgsConstructor
public class RegisterInitializer {

    private final InvoiceRegisterQueryRepository invoiceRegisters;
    private final PaymentRegisterQueryRepository paymentRegisters;
    private final InvoiceFacade facade;
    private final DataLoader loader;

    public void init() {
        loader.readData( "registers_invoice.txt")
            .forEach( register-> {
                if( !invoiceRegisters.existsByCode( register[0])){
                    facade.save( RegisterDto.create()
                            .code( register[0])
                            .type( InvoiceRegisterType.valueOf( register[1]))
                            .kind( register[2])
                            .name( register[3]));
                }
            });

        loader.readData( "registers_payment.txt")
            .forEach( register-> {
                if( !paymentRegisters.existsByCode( register[0])){
                    facade.save( RegisterDto.create()
                            .code( register[0])
                            .type( InvoiceRegisterType.valueOf( register[1]))
                            .name( register[2]));
                }
            });
    }
}
