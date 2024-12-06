package pl.janksiegowy.backend.accounting.decree.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.account.AccountSide;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;

import java.math.BigDecimal;

public interface DecreeLineDto {

    static Proxy create() {
        return new Proxy();
    }

    AccountSide getSide();
    AccountDto getAccount();
    BigDecimal getValue();
    String getDescription();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements DecreeLineDto {

        private AccountSide side;
        private AccountDto account;
        private BigDecimal value;
        private String description;

        @Override public AccountSide getSide() {
            return side;
        }

        @Override public AccountDto getAccount() {
            return account;
        }

        @Override public BigDecimal getValue() {
            return value;
        }

        @Override public String getDescription() {
            return description;
        }
    }
}
