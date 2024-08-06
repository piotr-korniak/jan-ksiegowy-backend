package pl.janksiegowy.backend.statement.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.statement.StatementItemCode;

import java.math.BigDecimal;
import java.util.UUID;

public interface StatementLineDto {

    static Proxy create() {
        return new Proxy();
    }

    StatementItemCode getItemCode();
    BigDecimal getAmount();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements StatementLineDto {

        private StatementItemCode itemCode;
        private BigDecimal amount;

        @Override public StatementItemCode getItemCode() {
            return itemCode;
        }
        @Override public BigDecimal getAmount() {
            return amount;
        }
    }
}
