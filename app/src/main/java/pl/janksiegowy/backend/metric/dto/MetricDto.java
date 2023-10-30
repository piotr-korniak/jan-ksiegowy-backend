package pl.janksiegowy.backend.metric.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.tenant.dto.TenantDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface MetricDto {

    static Proxy create() {
        return new Proxy();
    }

    LocalDate getId();
    BigDecimal getCapital();
    String getName();


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements MetricDto {

        private LocalDate id;
        private BigDecimal capital;
        private String name;

        @Override public LocalDate getId() {
            return id;
        }

        @Override public BigDecimal getCapital() {
            return capital;
        }

        @Override public String getName() {
            return name;
        }
    }
}
