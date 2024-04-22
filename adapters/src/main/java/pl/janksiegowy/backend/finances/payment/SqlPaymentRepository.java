package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface SqlPaymentRepository extends JpaRepository<PaymentOld, UUID> {
}

interface SqlPaymentQueryRepository extends PaymentQueryRepository, Repository<Payment, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class PaymentRepositoryImpl implements PaymentRepository {

    private final SqlPaymentRepository repository;
    @Override public PaymentOld save( PaymentOld payment) {
        return repository.save( payment);
    }
}