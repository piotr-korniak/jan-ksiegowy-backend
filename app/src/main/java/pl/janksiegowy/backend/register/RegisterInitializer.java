package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.finances.payment.PaymentFacade;
import pl.janksiegowy.backend.invoice.InvoiceFacade;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterQueryRepository;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterQueryRepository;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterType;
import pl.janksiegowy.backend.register.payment.PaymentRegisterFactory;
import pl.janksiegowy.backend.register.payment.PaymentRegisterQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;

import java.util.List;

@AllArgsConstructor
public class RegisterInitializer {

    private final InvoiceRegisterQueryRepository invoiceRegisters;
    private final PaymentRegisterQueryRepository paymentRegisters;
    private final AccountingRegisterQueryRepository accountingRegisters;
    private final DecreeFacade decree;
    private final InvoiceFacade invoice;
    private final PaymentFacade payment;
    private final DataLoader loader;

    public void initAccountingRegisters( List<RegisterDto> accountingRegister) {
        accountingRegister.forEach( registerDto-> {
            if( !accountingRegisters.existsByCode( registerDto.getCode())){
                decree.save( registerDto);
            }
        });
    }

    public void initInvoiceRegisters() {
        loader.readData( "registers_invoice.txt").forEach( register-> {
            if( !invoiceRegisters.existsByCode( register[0])){
                invoice.save( RegisterDto.create()
                    .code( register[0])
                    .type( register[1])
                    .kind( register[2])
                    .name( register[3]));
            }
        });
    }

    public void initPaymentRegisters() {
        loader.readData( "registers_payment.txt").forEach( register-> {
            if( !paymentRegisters.existsByCode( register[0])){
                payment.save( RegisterDto.create()
                    .code( register[0])
                    .type( register[1])
                    .name( register[2]));
            }
        });
    }

}
