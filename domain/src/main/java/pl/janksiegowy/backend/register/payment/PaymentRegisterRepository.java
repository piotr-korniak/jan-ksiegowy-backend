package pl.janksiegowy.backend.register.payment;

import pl.janksiegowy.backend.register.Register;

import java.util.List;
import java.util.Optional;

public interface PaymentRegisterRepository {

    PaymentRegister save( PaymentRegister register);

    Optional<Register> findByCode( String code);

    List<BankAccount> findBankAccounts();
}
