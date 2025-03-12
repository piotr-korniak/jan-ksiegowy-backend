package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.register.invoice.*;

import java.util.Optional;
import java.util.UUID;

public interface SqlInvoiceRegisterRepository extends JpaRepository<InvoiceRegister, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class InvoiceRegisterRepositoryImpl implements InvoiceRegisterRepository {

    private SqlInvoiceRegisterRepository repository;

    @Override public InvoiceRegister save( InvoiceRegister register) {
        return repository.save( register);
    }

}