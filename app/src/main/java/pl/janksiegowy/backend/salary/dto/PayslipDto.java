package pl.janksiegowy.backend.salary.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.period.dto.PeriodDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.Chronology;

public interface PayslipDto {

    static Proxy create() {
        return new Proxy();
    }

    String getNumber();
    LocalDate getDate();
    EntityDto getEntity();
    BigDecimal getGross();
    BigDecimal getInsuranceEmployee();
    BigDecimal getInsuranceEmployer();
    BigDecimal getInsuranceHealth();
    BigDecimal getTaxAdvance();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements PayslipDto {

        private String number;
        private LocalDate date;
        private EntityDto entity;
        private BigDecimal gross;
        private BigDecimal insuranceEmployee;
        private BigDecimal insuranceEmployer;
        private BigDecimal insuranceHealth;
        private BigDecimal taxAdvance;

        @Override public String getNumber() {
            return number;
        }
        @Override public LocalDate getDate() {
            return date;
        }

        @Override public EntityDto getEntity() {
            return entity;
        }

        @Override public BigDecimal getGross() {
            return gross;
        }
        @Override public BigDecimal getInsuranceEmployee() {
            return insuranceEmployee;
        }
        @Override public BigDecimal getInsuranceEmployer() {
            return insuranceEmployer;
        }
        @Override public BigDecimal getInsuranceHealth() {
            return insuranceHealth;
        }
        @Override public BigDecimal getTaxAdvance() {
            return taxAdvance;
        }

    }
}
