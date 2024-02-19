package pl.janksiegowy.backend.payment.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface ClearingDto {

    static Proxy create() {
        return new Proxy();
    }

    BigDecimal getAmount();
    UUID getReceivable();
    UUID getPayable();
    LocalDate getDate();


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements ClearingDto {

        BigDecimal amount;
        UUID receivable;
        UUID payable;
        LocalDate date;

        @Override public BigDecimal getAmount() {
            return amount;
        }
        @Override public UUID getReceivable() {
            return receivable;
        }
        @Override public UUID getPayable() {
            return payable;
        }
        @Override public LocalDate getDate() {
            return date;
        }
    }
}
