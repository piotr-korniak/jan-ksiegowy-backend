package pl.janksiegowy.backend.register.invoice;

import pl.janksiegowy.backend.register.Register;
import pl.janksiegowy.backend.register.payment.PaymentRegister;

import java.util.Optional;

public interface InvoiceRegisterRepository {

    public InvoiceRegister save( InvoiceRegister register);


}
