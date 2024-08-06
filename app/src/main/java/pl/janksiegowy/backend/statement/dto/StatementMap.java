package pl.janksiegowy.backend.statement.dto;

import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.statement.StatementKind;
import pl.janksiegowy.backend.statement.StatementStatus;
import pl.janksiegowy.backend.statement.StatementType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StatementMap implements StatementDto {

    private final StatementDto statement;
    private final List<StatementLineDto> lines;

    private StatementMap( StatementDto statement) {
        this.statement= statement;
        this.lines= Optional.ofNullable( statement.getStatementLines()).orElseGet( ArrayList::new);
    }

    public static StatementMap create( StatementDto statement) {
        return new StatementMap( statement );
    }

    @Override public UUID getStatementId() {
        return statement.getStatementId();
    }

    @Override public PatternId getPatternId() {
        return statement.getPatternId();
    }

    @Override public StatementType getType() {
        return statement.getType();
    }

    @Override public StatementKind getKind() {
        return statement.getKind();
    }

    @Override public LocalDateTime getCreated() {
        return statement.getCreated();
    }

    @Override public LocalDate getDate() {
        return statement.getDate();
    }

    @Override public LocalDate getDue() {
        return statement.getDue();
    }

    @Override public String getXml() {
        return statement.getXml();
    }

    @Override public BigDecimal getLiability() {
        return statement.getLiability();
    }

    @Override public String getNumber() {
        return statement.getNumber();
    }

    @Override public EntityDto getSettlementEntity() {
        return statement.getSettlementEntity();
    }

    @Override public Period getPeriod() {
        return statement.getPeriod();
    }

    @Override public BigDecimal getValue1() {
        return statement.getValue1();
    }

    @Override public BigDecimal getValue2() {
        return statement.getValue2();
    }

    @Override public int getNo() {
        return statement.getNo();
    }

    @Override public StatementStatus getStatus() {
        return statement.getStatus();
    }

    @Override public List<StatementLineDto> getStatementLines() {
        return lines;
    }

    public StatementMap addLine( StatementLineDto line) {
        lines.add( line);
        return this;
    }
}
