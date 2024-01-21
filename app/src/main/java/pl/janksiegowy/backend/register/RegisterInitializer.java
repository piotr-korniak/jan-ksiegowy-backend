package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.invoice.InvoiceFacade;
import pl.janksiegowy.backend.register.dto.VatRegisterDto;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.shared.DataLoader;

import java.util.List;

@AllArgsConstructor
public class RegisterInitializer {

    private final RegisterQueryRepository registers;
    private final InvoiceFacade facade;
    private final DataLoader loader;

    public void init() {
        loader.readData( "registers_vat.txt")
            .forEach( register-> {
                var type= RegisterType.valueOf( register[1]);

                if( !registers.existsByTypeInAndCode(
                        List.of( RegisterType.S, RegisterType.P), register[0])){
                    facade.save( VatRegisterDto.create()
                            .code( register[0])
                            .type( type)
                            .kind( InvoiceRegisterKind.valueOf( register[2]))
                            .name( register[3]));
                }
            });
    }
}
