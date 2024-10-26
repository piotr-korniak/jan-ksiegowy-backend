package pl.janksiegowy.backend.accounting.template.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.account.AccountPage;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.finances.settlement.SettlementType;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType;

import java.util.UUID;

public interface TemplateLineDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getId();
    AccountPage getPage();
    AccountDto getAccount();
    String getFunction();
    String getRegisterType();
    String getDescription();
    SettlementType getSettlementType();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements TemplateLineDto {

        private UUID id;
        private AccountPage page;
        private AccountDto account;
        private String function;
        private String registerType;
        private String description;
        private SettlementType settlementType;

        public Proxy function( PaymentFunction function) {
            this.function= function.name();
            return this;
        }
        public Proxy function( InvoiceFunction function) {
            this.function= function.name();
            return this;
        }
        public Proxy function( StatementFunction function) {
            this.function= function.name();
            return this;
        }
        public Proxy function( PayslipFunction function) {
            this.function= function.name();
            return this;
        }
        public Proxy function( SettlementFunction function) {
            this.function= function.name();
            return this;
        }

        public Proxy registerType( PaymentRegisterType registerType ) {
            this.registerType= registerType.name();
            return this;
        }

        @Override public UUID getId() {
            return id;
        }
        @Override public AccountPage getPage() {
            return page;
        }
        @Override public AccountDto getAccount() {
            return account;
        }
        @Override public String getFunction() {
            return function;
        }
        @Override public String getRegisterType() {
            return registerType;
        }
        @Override public String getDescription() {
            return description;
        }
        @Override public SettlementType getSettlementType() {
            return settlementType;
        }

    }
}
