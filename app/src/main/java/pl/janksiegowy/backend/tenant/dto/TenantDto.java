package pl.janksiegowy.backend.tenant.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

public interface TenantDto {
    static Proxy create() {
        return new Proxy();
    }

    UUID getId();
    String getCode();
    String getName();

    String getPassword();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements TenantDto {
        private UUID id;
        private String code;
        private String name;
        private String password;

        public Proxy code( String code) {
            this.code= code.toLowerCase();
            return this;
        }

        @Override public UUID getId() {
            return id;
        }
        @Override public String getCode() {
            return code;
        }
        @Override public String getName() {
            return name;
        }
        @Override public String getPassword() {
            return password;
        }
    }
}
