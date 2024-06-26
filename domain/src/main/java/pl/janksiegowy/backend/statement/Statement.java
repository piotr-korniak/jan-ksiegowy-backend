package pl.janksiegowy.backend.statement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.pattern.PatternId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "STATEMENTS")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class Statement {

    @Id
    @Column( name= "ID")
   // @UuidGenerator
    private UUID statementId;

/*
    @OneToOne( mappedBy= "statement", cascade = CascadeType.ALL)
    protected StatementSettlement settlement;
*/
    @ManyToOne( fetch= FetchType.EAGER)
    private Period period;

    @Enumerated( EnumType.STRING)
    private PatternId patternId;

    private LocalDate date;

    private LocalDateTime created;

    private String xml;

    private BigDecimal value_1;
    private BigDecimal value_2;

    private int no;

    @Enumerated( EnumType.STRING)
    private StatementStatus status= StatementStatus.N;

    public StatementKind getKind() {
        return StatementKind.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

    public Statement setStatementId( UUID statementId) {
        this.statementId= statementId;
        return this;
    }
/*
    public Statement setSettlement( StatementSettlement settlement) {
        this.settlement= settlement;
        settlement.setStatement( this);
        return this;
    }
*/
    public Statement setDate( LocalDate date) {
        this.date= date;
        return this;
    }

    public Statement setPattern( PatternId patternId) {
        this.patternId= patternId;
        return this;
    }

    public Statement setPeriod( Period period) {
        this.period= period;
        return this;
    }

    public Statement setCreated( LocalDateTime created) {
        this.created= created;
        return this;
    }

    public Statement setXML( String xml) {
        this.xml= xml;
        return this;
    }

    public String getNumber() {
        return "";
    }



/*
    public String getNumber() {
        return settlement.getNumber();
    }*/
}
