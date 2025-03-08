package pl.janksiegowy.backend.register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.register.accounting.AccountingRegister;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterQueryRepository;

import java.util.Optional;
import java.util.UUID;

public interface SqlAccountingRegisterRepository extends JpaRepository<AccountingRegister, UUID> {
    Optional<Register> findByCode( String code);
}

interface SqlAccountingRegisterQueryRepository extends AccountingRegisterQueryRepository,
        Repository<AccountingRegister, UUID> {

}
