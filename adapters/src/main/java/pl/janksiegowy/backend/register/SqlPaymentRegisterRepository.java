package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.register.payment.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface SqlPaymentRegisterRepository extends JpaRepository<PaymentRegister, UUID> {
    Optional<PaymentRegister> findByCode( String code);

    List<BankAccount> findByType( PaymentRegisterType paymentRegisterType);
}

interface SqlPaymentRegisterQueryRepository extends PaymentRegisterQueryRepository,
                                                    Repository<PaymentRegister, UUID> {}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class PaymentRegisterRepositoryImpl implements PaymentRegisterRepository {

    private SqlPaymentRegisterRepository repository;

    @Override public PaymentRegister save( PaymentRegister register ) {
        return repository.save( register);
    }

    @Override
    public Optional<PaymentRegister> findByCode( String code) {
        return repository.findByCode( code);
    }

    @Override
    public List<BankAccount> findBankAccounts() {
        return repository.findByType( PaymentRegisterType.B);
    }
}