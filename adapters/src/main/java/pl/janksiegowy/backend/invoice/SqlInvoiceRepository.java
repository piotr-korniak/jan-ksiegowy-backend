package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface SqlInvoiceRepository extends JpaRepository<Invoice, UUID> {
}

interface SqlCustomerInvoiceQueryRepository extends InvoiceQueryRepository, Repository<Invoice, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class InvoiceRepositoryImpl implements InvoiceRepository {

    private SqlInvoiceRepository repository;

    @Override
    public Invoice save( Invoice invoice) {
        return repository.save( invoice);
    }
}