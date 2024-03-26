package pl.janksiegowy.backend.accounting.decree;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import pl.janksiegowy.backend.accounting.account.Account;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.payment.PaymentReceipt;
import pl.janksiegowy.backend.finances.payment.PaymentSpend;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Accessors( chain= true)
@Getter

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

    private String description;

    public abstract <T> T accept( DecreeLineVisitor<T> visitor);

    public interface DecreeLineVisitor<T> {
        T visit( DecreeDtLine line);
        T visit( DecreeCtLine line);
    }
}

@Entity
@DiscriminatorValue( "D")
class DecreeDtLine extends DecreeLine {

    @Override public <T> T accept( DecreeLineVisitor<T> visitor ) {
        return visitor.visit( this);
    }
}

@Entity
@DiscriminatorValue( "C")
class DecreeCtLine extends DecreeLine {

    @Override public <T> T accept( DecreeLineVisitor<T> visitor ) {
        return visitor.visit( this);
    }
}
