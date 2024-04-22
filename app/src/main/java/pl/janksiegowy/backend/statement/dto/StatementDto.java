package pl.janksiegowy.backend.statement.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.statement.StatementStatus;
import pl.janksiegowy.backend.statement.StatementType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface StatementDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getStatementId();
    PatternId getPatternId();
    StatementType getType();
    LocalDateTime getCreated();
    LocalDate getDate();
    LocalDate getDue();
    String getXml();
    BigDecimal getLiability();
    String getNumber();
    EntityDto getSettlementEntity();
    String getPeriodId();
    BigDecimal getValue1();
    BigDecimal getValue2();
    int getNo();
    StatementStatus getStatus();


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements StatementDto {

        private UUID statementId;
        private PatternId patternId;
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
        private int no;
        private StatementStatus status;

        @Override public UUID getStatementId() {
            return statementId;
        }
        @Override public PatternId getPatternId() {
            return patternId;
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
        @Override public LocalDate getDue() {
            return due;
        }
        @Override public String getXml() {
            return xml;
        }
        @Override public BigDecimal getLiability() {
            return liability;
        }
        @Override public String getNumber() {
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
        @Override public int getNo() {
            return no;
        }
        @Override public StatementStatus getStatus() {
            return status;
        }

    }
}
