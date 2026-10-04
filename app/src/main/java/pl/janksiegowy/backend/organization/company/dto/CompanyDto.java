package pl.janksiegowy.backend.organization.company.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

public interface CompanyDto {
    static Proxy create() {
        return new Proxy();
    }

    UUID getId();
    String getCode();
    String getName();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements CompanyDto {
        private UUID id;
        private String code;
        private String name;

        @Override public UUID getId() {
            return id;
        }
        @Override public String getCode() {
            return code;
        }
        @Override public String getName() {
            return name;
        }
    }
}
