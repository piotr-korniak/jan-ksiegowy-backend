package pl.janksiegowy.backend.finances.settlement.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.finances.settlement.SettlementType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface SettlementDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getSettlementId();
    SettlementType getType();
    SettlementKind getKind();
    String getNumber();
    LocalDate getDate();
    LocalDate getDue();
    EntityDto getEntity();
    BigDecimal getDt();
    BigDecimal getCt();
    BigDecimal getAmount();

    List<ClearingDto> getClearings();
/*    List<Clearing> getReceivable();
    List<Clearing> getPayable();*/


    @Setter
    @Accessors(fluent = true, chain = true)
    class Proxy implements SettlementDto {

        private UUID settlementId;
        private SettlementType type;
        private SettlementKind kind;
        private String number;
        private LocalDate date;
        private LocalDate due;
        private EntityDto entity;
        private BigDecimal dt = BigDecimal.ZERO;
        private BigDecimal ct = BigDecimal.ZERO;
        private BigDecimal amount;
        private List<ClearingDto> clearings = new ArrayList<>();

        @Override
        public UUID getSettlementId() {
            return settlementId;
        }

        @Override
        public SettlementType getType() {
            return type;
        }

        @Override
        public SettlementKind getKind() {
            return kind;
        }

        @Override
        public String getNumber() {
            return number;
        }

        @Override
        public LocalDate getDate() {
            return date;
        }

        @Override public LocalDate getDue() {
            return due;
        }
        @Override public EntityDto getEntity() {
            return entity;
        }
        @Override public BigDecimal getDt() {
            return dt;
        }
        @Override public BigDecimal getCt() {
            return ct;
        }
        @Override public BigDecimal getAmount() {
            return amount;
        }

        @Override public List<ClearingDto> getClearings() {
            return clearings;
        }

    }
}
