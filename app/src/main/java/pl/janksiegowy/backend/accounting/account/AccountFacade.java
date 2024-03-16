package pl.janksiegowy.backend.accounting.account;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;

@AllArgsConstructor
public class AccountFacade {

    private final AccountFactory account;
    private final AccountRepository accounts;

    public Account save( AccountDto source) {
        return accounts.save( account.from( source));
    }
}
