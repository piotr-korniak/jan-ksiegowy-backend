package pl.janksiegowy.backend.accounting.decree.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.account.AccountPage;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;

import java.math.BigDecimal;

public interface DecreeLineDto {

    static Proxy create() {
        return new Proxy();
    }

    AccountPage getPage();
    AccountDto getAccount();
    BigDecimal getValue();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements DecreeLineDto {

        private AccountPage page;
        private AccountDto account;
        private BigDecimal value;

        @Override public AccountPage getPage() {
            return page;
        }

        @Override public AccountDto getAccount() {
            return account;
        }

        @Override public BigDecimal getValue() {
            return value;
        }
    }
}
