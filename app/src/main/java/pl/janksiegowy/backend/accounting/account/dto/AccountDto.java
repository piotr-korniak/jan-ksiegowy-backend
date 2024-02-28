package pl.janksiegowy.backend.accounting.account.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.account.AccountType;

import java.util.UUID;

public interface AccountDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getAccountId();
    String getParent();
    String getNumber();
    AccountType getType();
    String getName();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements AccountDto {

        private UUID accountId;
        private String parent;
        private String number;
        private AccountType type;
        private String name;

        @Override public UUID getAccountId() {
            return accountId;
        }

        @Override public String getParent() {
            return parent;
        }

        @Override public String getNumber() {
            return number;
        }
        @Override public AccountType getType() {
            return type;
        }

        @Override public String getName() {
            return name;
        }

    }
}
