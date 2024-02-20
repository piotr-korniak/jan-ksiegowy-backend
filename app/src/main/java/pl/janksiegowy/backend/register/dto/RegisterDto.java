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
    InvoiceRegisterType getType();
    String getKind();
    String getName();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements RegisterDto {

        private UUID registerId;
        private String code;
        private InvoiceRegisterType type;
        private String kind;
        private String name;

        @Override public UUID getRegisterId() {
            return registerId;
        }

        @Override public String getCode() {
            return code;
        }

        @Override public InvoiceRegisterType getType() {
            return type;
        }

        public String getKind() {
            return kind;
        }

        @Override public String getName() {
            return name;
        }
    }
}