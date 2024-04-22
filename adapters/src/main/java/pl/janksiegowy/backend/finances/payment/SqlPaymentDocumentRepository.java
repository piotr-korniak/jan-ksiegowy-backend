package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.janksiegowy.backend.finances.document.Document;

import java.util.UUID;

public interface SqlPaymentDocumentRepository extends JpaRepository<Document, UUID> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class PaymentDocumentRepositoryImpl implements PaymentDocumentRepository {

    private final SqlPaymentDocumentRepository repository;
    @Override public Payment save( Payment payment) {
        return repository.save( payment);
    }
}
