package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingId;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface SqlPaymentRepository extends JpaRepository<Payment, UUID> {
}

interface SqlPaymentQueryRepository extends PaymentQueryRepository, Repository<Payment, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class PaymentRepositoryImpl implements PaymentRepository {

    private final SqlPaymentRepository repository;
    @Override public Payment save( Payment payment) {
        return repository.save( payment);
    }
}