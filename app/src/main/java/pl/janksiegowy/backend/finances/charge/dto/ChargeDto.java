package pl.janksiegowy.backend.finances.charge.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.charge.ChargeType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface ChargeDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getChargeId();
    ChargeType getType();
    String getNumber();
    LocalDate getDate();
    LocalDate getDue();
    EntityDto getEntity();
    BigDecimal getAmount();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements ChargeDto {

        private UUID chargeId;
        private ChargeType type;
        private String number;
        private LocalDate date;
        private LocalDate due;
        private EntityDto entity;
        private BigDecimal amount;

        @Override public UUID getChargeId() {
            return chargeId;
        }
        @Override public LocalDate getDate() {
            return date;
        }
        @Override public LocalDate getDue() {
            return due;
        }
        @Override public ChargeType getType() {
            return type;
        }
        @Override  public String getNumber() {
            return number;
        }
        @Override public EntityDto getEntity() {
            return entity;
        }
        @Override public BigDecimal getAmount() {
            return amount;
        }

    }
}
