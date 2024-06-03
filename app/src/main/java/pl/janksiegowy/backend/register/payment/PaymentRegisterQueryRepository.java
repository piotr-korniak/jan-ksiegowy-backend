package pl.janksiegowy.backend.register.payment;

import pl.janksiegowy.backend.register.RegisterQueryRepository;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.util.List;
import java.util.Optional;

public interface PaymentRegisterQueryRepository  extends RegisterQueryRepository {

    <T> List<T> findByType( Class<T> dto, PaymentRegisterType type);

}
