package pl.janksiegowy.backend.statement;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.settlement.StatementSettlement;
import pl.janksiegowy.backend.shared.pattern.PatternCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter

@Entity
@Table( name= "STATEMENTS")
public class Statement {

    @Id
    @Column( name= "ID")
    private UUID statementId;

    @OneToOne( mappedBy= "statement", cascade = CascadeType.ALL)
    protected StatementSettlement settlement;

    @ManyToOne( fetch= FetchType.LAZY)
    private Period period;

    @Enumerated( EnumType.STRING)
    private PatternCode patternId;

    private LocalDate date;

    private LocalDateTime created;

    private String xml;

    public Statement setStatementId( UUID statementId) {
        this.statementId= statementId;
        settlement.setStatement( this);
        return this;
    }

    public Statement setSettlement( StatementSettlement settlement) {
        this.settlement= settlement;
        settlement.setStatement( this);
        return this;
    }

    public Statement setDate( LocalDate date) {
        this.date= date;
        return this;
    }

    public Statement setPattern( PatternCode patternCode) {
        this.patternId = patternCode;
        return this;
    }

    public Statement setPeriod( Period period ) {
        this.period= period;
        return this;
    }

    public Statement setCreated( LocalDateTime created) {
        this.created= created;
        return this;
    }
}
