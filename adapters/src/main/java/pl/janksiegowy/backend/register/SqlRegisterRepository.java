package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.invoice.InvoiceQueryRepository;

import java.util.Optional;
import java.util.UUID;

public interface SqlRegisterRepository extends JpaRepository<Register, UUID> {

    Optional<Register> findByCode( String code);
    Optional<Register> findByTypeAndRegisterId( RegisterType type, UUID registerId);
}

interface SqlRegisterQueryRepository extends RegisterQueryRepository, Repository<Register, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class RegisterRepositoryImpl implements RegisterRepository {

    private final SqlRegisterRepository repository;

    @Override public Optional<Register> findByCode( String code) {
        return repository.findByCode( code);
    }

    @Override
    public Optional<Register> findByTypeAndRegisterId( RegisterType type, UUID registerId) {
        return repository.findByTypeAndRegisterId( type, registerId);
    }

    @Override public Register save( Register register) {
        return repository.save( register);
    }
}
