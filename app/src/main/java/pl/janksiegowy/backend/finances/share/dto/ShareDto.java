package pl.janksiegowy.backend.finances.share.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.charge.ChargeType;
import pl.janksiegowy.backend.finances.charge.dto.ChargeDto;
import pl.janksiegowy.backend.finances.share.ShareType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface ShareDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getChargeId();
    ShareType getType();
    String getNumber();
    LocalDate getDate();
    EntityDto getEntity();
    BigDecimal getEquity();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements ShareDto {
        private UUID chargeId;
        private ShareType type;
        private String number;
        private LocalDate date;
        private EntityDto entity;
        private BigDecimal equity;

        @Override public UUID getChargeId() {
            return chargeId;
        }

        @Override public ShareType getType() {
            return type;
        }

        @Override public String getNumber() {
            return number;
        }

        @Override public LocalDate getDate() {
            return date;
        }

        @Override public EntityDto getEntity() {
            return entity;
        }

        @Override public BigDecimal getEquity() {
            return equity;
        }
    }

}
