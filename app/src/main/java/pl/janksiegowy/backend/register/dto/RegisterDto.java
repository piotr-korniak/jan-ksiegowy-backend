package pl.janksiegowy.backend.register.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.register.RegisterType;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterType;
import pl.janksiegowy.backend.shared.financial.Currency;

import java.util.Optional;
import java.util.UUID;

public interface RegisterDto {
    static Proxy create() {
        return new Proxy();
    }

    UUID getRegisterId();
    String getCode();
    RegisterType getType();
    default InvoiceRegisterKind getKind(){
        return null;
    };

    String getName();
    String getLedgerAccountNumber();
    default String getBankAccountNumber() {
        return null;
    };
    default Currency getCurrency() {
        return null;
    }

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements RegisterDto {

        private UUID registerId;

        @JsonProperty( "Code")
        private String code;

        @JsonProperty( "Type")
        private RegisterType type;

        @JsonProperty( "Kind")
        private InvoiceRegisterKind kind;

        @JsonProperty( "Name")
        private String name;

        private String ledgerAccountNumber;

        @JsonProperty( "Account Number")
        private String bankAccountNumber;

        @JsonProperty( "Currency")
        private Currency currency;

        @Override public UUID getRegisterId() {
            return registerId;
        }

        @Override public String getCode() {
            return code;
        }

        @Override public RegisterType getType() {
            return type;
        }

        @Override public InvoiceRegisterKind getKind() {
            return kind;
        }

        @Override public String getName() {
            return name;
        }

        @Override public String getLedgerAccountNumber() {
            return ledgerAccountNumber;
        }

        @Override
        public String getBankAccountNumber() {
            return bankAccountNumber;
        }

        @Override public Currency getCurrency() {
            return currency;
        }

    }
}
