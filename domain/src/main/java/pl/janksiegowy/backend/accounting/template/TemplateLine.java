package pl.janksiegowy.backend.accounting.template;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import pl.janksiegowy.backend.accounting.account.Account;
import pl.janksiegowy.backend.accounting.account.AccountSide;
import pl.janksiegowy.backend.finances.settlement.SettlementType;

import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "TEMPLATES_LINES")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class TemplateLine {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    private Template template;

    @Enumerated( EnumType.STRING)
    private AccountSide side;

    @Enumerated( EnumType.STRING)
    private SettlementType settlementType;

    @ManyToOne
    private Account account;

    private String parameter;

    private String description;

}
