package pl.janksiegowy.backend.register.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterType;

import java.util.UUID;

public interface RegisterDto {
    static Proxy create() {
        return new Proxy();
    }

    UUID getRegisterId();
    String getCode();
    String getType();
    String getKind();
    String getName();
    String getAccountNumber();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements RegisterDto {

        private UUID registerId;
        private String code;
        private String type;
        private String kind;
        private String name;
        private String accountNumber;

        @Override public UUID getRegisterId() {
            return registerId;
        }

        @Override public String getCode() {
            return code;
        }

        @Override public String getType() {
            return type;
        }

        @Override public String getKind() {
            return kind;
        }

        @Override public String getName() {
            return name;
        }

        @Override public String getAccountNumber() {
            return accountNumber;
        }
    }
}
