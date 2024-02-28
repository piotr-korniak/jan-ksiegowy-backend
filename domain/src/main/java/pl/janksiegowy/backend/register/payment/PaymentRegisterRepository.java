package pl.janksiegowy.backend.register.payment;

import pl.janksiegowy.backend.register.Register;

import java.util.Optional;

public interface PaymentRegisterRepository {

    PaymentRegister save( PaymentRegister register);

    Optional<PaymentRegister> findByCode( String code);
}
