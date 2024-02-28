package pl.janksiegowy.backend.accounting.decree;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.account.Account;
import pl.janksiegowy.backend.invoice_line.InvoiceLine;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.register.accounting.AccountingRegister;

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
public abstract class Decree {

    @Id
    @Column( name= "ID")
    private UUID decreeId;

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

}

@Entity
@DiscriminatorValue( "B")
class BasicDecree extends Decree {
}

@Entity
@DiscriminatorValue( "S")
class SettlementDecree extends Decree {
}