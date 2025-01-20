package pl.janksiegowy.backend.accounting.template.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.account.AccountSide;
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
    AccountSide getSide();
    String getAccountNumber();
    AccountDto getAccount();

    String getFunction();
    String getDescription();
    SettlementType getSettlementType();

    @Setter
    @Accessors( fluent= true, chain= true)
    //@JsonIgnoreProperties( ignoreUnknown = true)
    class Proxy implements TemplateLineDto {

        private UUID id;
        private AccountSide side;

        @JsonProperty( "accountNumber")
        private String accountNumber;

        @JsonIgnore
        private AccountDto account;

        private String function;
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

        @Override public UUID getId() {
            return id;
        }
        @Override public AccountSide getSide() {
            return side;
        }

        @Override
        public String getAccountNumber() {
            return accountNumber;
        }

        @Override public AccountDto getAccount() {
            return account;
        }
        @Override public String getFunction() {
            return function;
        }
        @Override public String getDescription() {
            return description;
        }
        @Override public SettlementType getSettlementType() {
            return settlementType;
        }

    }
}
