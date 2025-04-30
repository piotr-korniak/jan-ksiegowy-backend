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
        return source.getType().accept( this)
                .setId( Optional.ofNullable( source.getId()).orElseGet( UUID::randomUUID))
                .setParent( Optional.ofNullable( source.getParent())
                        .map( parentNumber-> accounts.findByNumber( parentNumber)
                                .orElseThrow())
                        .orElseGet(()-> null))
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
