package pl.janksiegowy.backend.accounting.account;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.account.AccountType.AccountTypeVisitor;

import java.lang.management.OperatingSystemMXBean;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class AccountFactory implements AccountTypeVisitor<Account> {
    private final AccountRepository accounts;

    public Account from( AccountDto source) {

        return update( source, source.getType().accept( this)
                .setAccountId( UUID.randomUUID()));
    }

    private Account update( AccountDto source, Account account) {
        Optional.ofNullable( source.getParent())
                .ifPresent( numberParent-> account.setParent( accounts.findByNumber( numberParent)
                        .orElseThrow()));

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
