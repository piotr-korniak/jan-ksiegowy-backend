package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.register.accounting.AccountingRegister;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterQueryRepository;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;
import pl.janksiegowy.backend.register.invoice.InvoiceRegister;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterRepository;

import java.util.Optional;
import java.util.UUID;

public interface SqlAccountingRegisterRepository extends JpaRepository<AccountingRegister, UUID> {
    Optional<AccountingRegister> findByCode( String code);
}

interface SqlAccountingRegisterQueryRepository extends AccountingRegisterQueryRepository,
        Repository<AccountingRegister, UUID> {}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class AccountingRegisterRepositoryImpl implements AccountingRegisterRepository {

    private final SqlAccountingRegisterRepository repository;
    @Override public AccountingRegister save( AccountingRegister register) {
        return repository.save( register);
    }

    @Override public Optional<AccountingRegister> findByCode( String code) {
        return repository.findByCode( code);
    }

    @Override public Optional<AccountingRegister> findById( UUID registerId) {
        return repository.findById( registerId);
    }
}