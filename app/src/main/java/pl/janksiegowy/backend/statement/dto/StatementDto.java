package pl.janksiegowy.backend.statement.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.shared.pattern.PatternCode;
import pl.janksiegowy.backend.statement.StatementType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiFunction;

public interface StatementDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getStatementId();
    PatternCode getPatternId();
    StatementType getType();
    LocalDateTime getCreated();
    LocalDate getDate();
    LocalDate getSettlementDue();
    String getXml();
    BigDecimal getSettlementCt();
    String getSettlementNumber();
    EntityDto getSettlementEntity();
    String getPeriodId();
    BigDecimal getValue1();
    BigDecimal getValue2();


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements StatementDto {

        private UUID statementId;
        private PatternCode patternCode;
        private StatementType type;
        private LocalDateTime created;
        private LocalDate date;
        private LocalDate due;
        private String xml;
        private BigDecimal liability;
        private String number;
        private EntityDto revenue;
        private String periodId;
        private BigDecimal value1;
        private BigDecimal value2;

        @Override public UUID getStatementId() {
            return statementId;
        }
        @Override public PatternCode getPatternId() {
            return patternCode;
        }
        @Override public StatementType getType() {
            return type;
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
        @Override public BigDecimal getValue1() {
            return value1;
        }
        @Override public BigDecimal getValue2() {
            return value2;
        }

    }
}
