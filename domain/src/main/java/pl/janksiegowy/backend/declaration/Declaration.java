package pl.janksiegowy.backend.declaration;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.invoice.InvoiceType;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.salary.WageIndicatorCode;
import pl.janksiegowy.backend.salary.WageIndicatorConverter;
import pl.janksiegowy.backend.shared.pattern.PatternId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "DECLARATIONS")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class Declaration {

    @Id
    @Column( name= "ID")
    private UUID statementId;

    @ManyToOne( fetch= FetchType.LAZY)
    private Metric metric;

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private DeclarationType type;

    @ManyToOne( fetch= FetchType.EAGER)
    private Period period;

    @Column( name= "PATTERN_ID", insertable = false, updatable = false)
    private String pattern;

    @Column( name= "PATTERN_ID")
    @Enumerated( EnumType.STRING)
    private PatternId patternId;

    private LocalDate date;

    private LocalDateTime created;

    private String xml;

    private int no;

    @Enumerated( EnumType.STRING)
    private StatementStatus status= StatementStatus.N;

    @ElementCollection
    @CollectionTable( name= "DECLARATIONS_ELEMENTS", joinColumns= @JoinColumn( name= "DECLARATION_ID"))
    @MapKeyColumn( name= "ELEMENT_CODE" )
    @Column( name= "AMOUNT")
    @Convert( attributeName= "key", converter= DeclarationElementConverter.class)
    private Map<DeclarationElementCode, BigDecimal> elements= new HashMap<>();

    public DeclarationType getType() {
        return DeclarationType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

    public Declaration setStatementId(UUID statementId) {
        this.statementId= statementId;
        return this;
    }

    public Declaration setDate(LocalDate date) {
        this.date= date;
        return this;
    }

    public Declaration setPattern(PatternId patternId) {
        this.patternId= patternId;
        return this;
    }

    public Declaration setPeriod(Period period) {
        this.period= period;
        return this;
    }

    public Declaration setCreated(LocalDateTime created) {
        this.created= created;
        return this;
    }

    public Declaration setXML(String xml) {
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
