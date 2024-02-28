package pl.janksiegowy.backend.accounting.decree;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import pl.janksiegowy.backend.accounting.account.Account;
import pl.janksiegowy.backend.accounting.template.Template;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table( name= "DECREES_LINES")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "PAGE", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class DecreeLine {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    private Decree decree;

    @ManyToOne
    private Account account;

    private BigDecimal value;

    public DecreeLine setDecree( Decree decree ) {
        this.decree= decree;
        return this;
    }
}

@Entity
@DiscriminatorValue( "D")
class DecreeDtLine extends DecreeLine {

}

@Entity
@DiscriminatorValue( "C")
class DecreeCtLine extends DecreeLine {

}
