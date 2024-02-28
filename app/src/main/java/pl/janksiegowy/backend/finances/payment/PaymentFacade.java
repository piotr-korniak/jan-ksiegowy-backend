package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.accounting.decree.DecreeFactory;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingFactory;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.payment.dto.PaymentDto;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterFactory;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegister;
import pl.janksiegowy.backend.register.payment.PaymentRegisterFactory;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;

import java.util.Optional;

@AllArgsConstructor
public class PaymentFacade {

    private final PaymentFactory payment;
    private final PaymentRepository payments;

    private final ClearingFactory clearing;
    private final ClearingRepository clearings;

    private final PaymentRegisterFactory register;
    private final PaymentRegisterRepository registers;


    public Clearing save( ClearingDto source ) {
        return clearings.save( clearing.from( source));
    }

    public Payment save( PaymentDto source ) {
        return payments.save( payment.from( source));
    }

    public PaymentRegister save( RegisterDto source) {
        return registers.save( Optional.ofNullable( source.getRegisterId())
                .map( uuid -> register.update( source))
                .orElse( register.from( source)));
    }
}
