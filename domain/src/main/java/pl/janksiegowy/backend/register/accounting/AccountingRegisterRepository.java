package pl.janksiegowy.backend.register.accounting;

import pl.janksiegowy.backend.register.payment.PaymentRegister;

import java.util.Optional;
import java.util.UUID;

public interface AccountingRegisterRepository {
    AccountingRegister save( AccountingRegister register);
    Optional<AccountingRegister> findByCode( String code);

    Optional<AccountingRegister>  findById( UUID registerId);
}
