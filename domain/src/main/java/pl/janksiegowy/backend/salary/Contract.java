package pl.janksiegowy.backend.salary;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.invoice.InvoiceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "CONTRACTS")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
public abstract class Contract {

    @Id
    @Column( name= "ID")
    private UUID contractId;

    private LocalDate begin;
    @Column( name= "\"END\"")
    private LocalDate end;

    private String number;
    private BigDecimal salary;

    @ManyToOne( fetch= FetchType.EAGER)
    private pl.janksiegowy.backend.entity.Entity entity;

    @Enumerated( EnumType.STRING)
    @Column( insertable= false, updatable= false)
    private ContractType type;

    public ContractType getType() {
        return ContractType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

    public Contract setSalary( BigDecimal salary) {
        this.salary= salary;
        return this;
    }

    public Contract setEntity( pl.janksiegowy.backend.entity.Entity entity) {
        this.entity= entity;
        return this;
    }
}
