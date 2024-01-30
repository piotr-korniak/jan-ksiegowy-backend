package pl.janksiegowy.backend.statement.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.shared.pattern.PatternCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface StatementDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getStatementId();
    PatternCode getPatternId();
    LocalDateTime getCreated();
    LocalDate getDate();
    LocalDate getSettlementDue();
    String getXml();
    BigDecimal getSettlementCt();
    String getSettlementNumber();
    EntityDto getSettlementEntity();
    String getPeriodId();


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements StatementDto {

        private UUID statementId;
        private PatternCode patternCode;
        private LocalDateTime created;
        private LocalDate date;
        private LocalDate due;
        private String xml;
        private BigDecimal liability;
        private String number;
        private EntityDto revenue;
        private String periodId;

        @Override public UUID getStatementId() {
            return statementId;
        }
        @Override public PatternCode getPatternId() {
            return patternCode;
        }
        @Override public LocalDateTime getCreated() {
            return created;
        }
        @Override public LocalDate getDate() {
            return date;
        }
        @Override public LocalDate getSettlementDue() {
            return due;
        }
        @Override public String getXml() {
            return xml;
        }
        @Override public BigDecimal getSettlementCt() {
            return liability;
        }
        @Override public String getSettlementNumber() {
            return number;
        }
        @Override public EntityDto getSettlementEntity() {
            return revenue;
        }
        @Override public String getPeriodId() {
            return periodId;
        }

    }
}
