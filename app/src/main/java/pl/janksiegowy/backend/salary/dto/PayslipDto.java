package pl.janksiegowy.backend.salary.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

public interface PayslipDto {

    static Proxy create() {
        return new Proxy();
    }

    BigDecimal getInsuranceEmployee();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements PayslipDto {

        private BigDecimal insuranceEmployee;

        @Override public BigDecimal getInsuranceEmployee() {
            return insuranceEmployee;
        }
    }
}
