package pl.janksiegowy.backend.declaration.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.declaration.DeclarationElementCode;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.declaration.StatementStatus;
import pl.janksiegowy.backend.declaration.DeclarationType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface StatementDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getStatementId();
    PatternId getPatternId();
    DeclarationType getType();
    LocalDateTime getCreated();
    LocalDate getDate();
    LocalDate getDue();
    String getXml();
    BigDecimal getLiability();
    String getNumber();
    EntityDto getSettlementEntity();

    Period getPeriod();

    int getNo();
    StatementStatus getStatus();

    Map<DeclarationElementCode, BigDecimal> getElements();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements StatementDto {

        private UUID statementId;
        private PatternId patternId;
        private DeclarationType type;
        private LocalDateTime created;
        private LocalDate date;
        private LocalDate due;
        private String xml;
        private BigDecimal liability;
        private String number;
        private EntityDto revenue;
        private Period period;
        private int no;
        private StatementStatus status;
        private Map<DeclarationElementCode, BigDecimal> elements= new HashMap<>();

        @Override public UUID getStatementId() {
            return statementId;
        }
        @Override public PatternId getPatternId() {
            return patternId;
        }
        @Override public DeclarationType getType() {
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
        @Override public Period getPeriod() {
            return period;
        }
        @Override public int getNo() {
            return no;
        }
        @Override public StatementStatus getStatus() {
            return status;
        }
        @Override public Map<DeclarationElementCode, BigDecimal> getElements() {
            return elements;
        }

    }
}
