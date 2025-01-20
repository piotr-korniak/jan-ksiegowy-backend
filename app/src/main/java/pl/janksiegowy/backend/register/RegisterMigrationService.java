package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.finances.payment.PaymentFacade;
import pl.janksiegowy.backend.invoice.InvoiceFacade;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterQueryRepository;
import pl.janksiegowy.backend.register.dto.BankAccountDto;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterQueryRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.financial.Currency;

import java.util.List;

@Service
@AllArgsConstructor
public class RegisterMigrationService {

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

    public String initPaymentRegisters() {
        var counters= new Object() {
            int total= 0;
            int added= 0;
        };
        loader.readData( "registers_payment.csv").forEach( fields-> {
            counters.total++;
            if( !paymentRegisters.existsByCode( fields[0])){
                payment.save( addNumberIfAvailable( fields,
                        (BankAccountDto.Proxy) BankAccountDto.create()
                                .code( fields[0])
                                .type( fields[1])
                                .name( fields[2])));
                counters.added++;
            }
        });
        return String.format( "%-50s %13s", "Registers Payment configuration complete, added: ",
                counters.added+ "/"+ counters.total);
    }

    private BankAccountDto.Proxy addNumberIfAvailable(  String[] fields, BankAccountDto.Proxy register) {
        return fields.length >= 5
                ? register.number( fields[3]).currency( Currency.valueOf( fields[4]))
                : register;
    }

}
