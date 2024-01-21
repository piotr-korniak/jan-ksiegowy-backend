package pl.janksiegowy.backend.register.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.register.RegisterType;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;

import java.util.UUID;

public interface VatRegisterDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getRegisterId();
    String getCode();
    RegisterType getType();
    InvoiceRegisterKind getKind();
    String getName();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements VatRegisterDto {

        private UUID registerId;
        private String code;
        private RegisterType type;
        private InvoiceRegisterKind kind;
        private String name;

        @Override public UUID getRegisterId() {
            return registerId;
        }

        @Override public String getCode() {
            return code;
        }

        @Override public RegisterType getType() {
            return type;
        }

        public InvoiceRegisterKind getKind() {
            return kind;
        }

        @Override public String getName() {
            return name;
        }
    }
}
