package pl.janksiegowy.backend.accounting.account;

import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.account.AccountType.AccountTypeVisitor;

import java.util.UUID;

public class AccountFactory implements AccountTypeVisitor<Account> {
    public Account from( AccountDto source) {

        return update( source, source.getType().accept( this)
                .setAccountId( UUID.randomUUID()));
    }

    private Account update( AccountDto source, Account account) {
        return account
                .setNumber( source.getNumber())
                .setName( source.getName());
    }

    @Override public Account visitBalanceAccount() {
        return new BalanceAccount();
    }

    @Override public Account visitSettlementAccount() {
        return new SettlementAccount();
    }

    @Override public Account visitProfiAndLossAccount() {
        return new ProfitAccount();
    }
}
