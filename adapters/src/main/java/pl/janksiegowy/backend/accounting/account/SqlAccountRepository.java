package pl.janksiegowy.backend.accounting.account;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SqlAccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByNumber( String number);

    boolean existsByNumber( String number );
}

interface SqlAccountQueryRepository extends AccountQueryRepository, Repository<Account, UUID> {

    @Override
    @Query( "SELECT a1 FROM Account a1 LEFT JOIN Account a2 ON a1.id = a2.parent.id " +
            "WHERE a2.id IS NULL " +
            "AND TYPE(a1) = :type")
    List<AccountDto> findAllAnalyticalAccountsByType( @Param("type") Class<? extends Account> type);
}

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