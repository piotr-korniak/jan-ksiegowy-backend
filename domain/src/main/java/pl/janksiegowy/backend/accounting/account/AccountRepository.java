package pl.janksiegowy.backend.accounting.account;

import java.util.Optional;

public interface AccountRepository {
    Account save( Account account);

    Optional<Account> findByNumber( String number);
}
