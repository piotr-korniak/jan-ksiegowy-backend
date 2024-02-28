package pl.janksiegowy.backend.accounting.template.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.account.AccountPage;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.template.TemplateType;

import java.util.UUID;

public interface TemplateLineDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getId();
    TemplateType getType();
    AccountPage getPage();
    AccountDto getAccount();
    String getFunction();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements TemplateLineDto {

        private UUID id;
        private TemplateType type;
        private AccountPage page;
        private AccountDto account;
        private String function;

        @Override public UUID getId() {
            return id;
        }
        @Override public TemplateType getType() {
            return type;
        }
        @Override
        public AccountPage getPage() {
            return page;
        }
        @Override public AccountDto getAccount() {
            return account;
        }
        @Override public String getFunction() {
            return function;
        }
    }
}
