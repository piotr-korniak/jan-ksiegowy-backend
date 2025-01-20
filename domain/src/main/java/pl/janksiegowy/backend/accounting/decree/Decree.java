package pl.janksiegowy.backend.accounting.decree;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.settlement.Settlement;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.register.accounting.AccountingRegister;
import pl.janksiegowy.backend.accounting.decree.DecreeLine.DecreeLineVisitor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "DECREES")

@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class Decree implements DecreeLineVisitor<Decree> {

    @Id
    @Column( name= "ID")
    protected UUID decreeId;

    private LocalDate date;

    private String number;
    private String document;

    @ManyToOne( fetch= FetchType.LAZY)
    private AccountingRegister register;

    @ManyToOne( fetch= FetchType.LAZY)
    private MonthPeriod period;

    private BigDecimal dt= BigDecimal.ZERO;
    private BigDecimal ct= BigDecimal.ZERO;

    @OneToMany( fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval= true)
    @OrderColumn( name= "NO")
    @JoinColumn( name= "DECREE_ID")
    private List<DecreeLine> lines;

    public Decree setLines( List<DecreeLine> lines) {
        dt= BigDecimal.ZERO;
        ct= BigDecimal.ZERO;

        lines.forEach( decreeLine-> decreeLine.accept( this));
        this.lines= lines;
        return this;
    }

    @Override public Decree visit( DecreeDtLine line) {
        dt= dt.add( line.getValue());
        return this;
    }

    @Override public Decree visit( DecreeCtLine line) {
        ct= ct.add( line.getValue());
        return this;
    }
}

