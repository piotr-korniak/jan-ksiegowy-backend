package pl.janksiegowy.backend.salary.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.salary.PayslipItemCode;

import java.math.BigDecimal;
import java.util.UUID;

public interface PayslipLineDto {
    static Proxy create() {
        return new Proxy();
    }

    PayslipItemCode getItemCode();
    BigDecimal getAmount();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements PayslipLineDto {

        private PayslipItemCode itemCode;
        private BigDecimal amount;

        @Override public PayslipItemCode getItemCode() {
            return itemCode;
        }
        @Override public BigDecimal getAmount() {
            return amount;
        }
    }
}
