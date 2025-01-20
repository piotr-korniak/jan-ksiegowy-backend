package pl.janksiegowy.backend.accounting.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.account.AccountType;

import java.util.UUID;

public interface AccountDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getId();
    String getParent();
    String getNumber();
    AccountType getType();
    String getName();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements AccountDto {

        private UUID id;
        private String parent;

        @JsonProperty( "Number")
        private String number;

        @JsonProperty( "Type")
        private AccountType type;

        @JsonProperty( "Name")
        private String name;

        @Override public UUID getId() {
            return id;
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
