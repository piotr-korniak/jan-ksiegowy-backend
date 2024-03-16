package pl.janksiegowy.backend.accounting.account;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface SqlAccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByNumber( String number);

    boolean existsByNumber( String number );
}

interface SqlAccountQueryRepository extends AccountQueryRepository, Repository<Account, UUID> {}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class AccountRepositoryImpl implements AccountRepository {

    private final SqlAccountRepository repository;

    @Override public Account save( Account account) {
        return repository.save( account);
    }

    @Override public Optional<Account> findByNumber( String number ) {
        return repository.findByNumber( number);
    }

    @Override public boolean existsByNumber( String number ) {
        return repository.existsByNumber( number);
    }
}