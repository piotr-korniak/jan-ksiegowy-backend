package pl.janksiegowy.backend.company.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

public interface CompanyDto {
    static Proxy create() {
        return new Proxy();
    }

    long getId();
    String getCode();
    String getName();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements CompanyDto {
        private long id;
        private String code;
        private String name;

        @Override public long getId() {
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
