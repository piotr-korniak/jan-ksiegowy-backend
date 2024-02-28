package pl.janksiegowy.backend.accounting.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name = "ACCOUNTS")

@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class Account {

    @Id
    @Column( name= "ID")
    private UUID accountId;

    private String number;
    private String name;

}

@Entity
@DiscriminatorValue( "B")
class BalanceAccount extends Account {
}

@Entity
@DiscriminatorValue( "S")
class SettlementAccount extends Account {
}

@Entity
@DiscriminatorValue( "P")
class ProfitAccount extends Account {
}