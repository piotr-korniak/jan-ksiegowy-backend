package pl.janksiegowy.backend.accounting.template;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.payment.PaymentType;
import pl.janksiegowy.backend.finances.settlement.SettlementType;
import pl.janksiegowy.backend.invoice.InvoiceType;
import pl.janksiegowy.backend.register.accounting.AccountingRegister;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "TEMPLATES")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class Template {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private long id;
    private UUID templateId;
    private LocalDate date;

    private String code;

    @Enumerated( EnumType.STRING)
    private SettlementType context;

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private TemplateType type;

    @ManyToOne
    private AccountingRegister register;

    private String name;

    private String kind;

    @OneToMany( fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval= true)
    //@OrderColumn( name= "NO")
    @JoinColumn( name= "TEMPLATE_ID")
    private List<TemplateLine> items;

    public abstract <T> T accept( TemplateVisitor<T> visitor);

    public interface TemplateVisitor<T> {
        T visit( PaymentTemplate template);
        T visit( SalesInvoiceTemplate template);
    }

}

@Getter
@Setter
@Accessors( chain= true)

@Entity
@DiscriminatorValue( "B")
class BankAccountTemplate extends PaymentTemplate {
    @Override public <T> T accept( TemplateVisitor<T> visitor) {
        return visitor.visit( this);
    }
}

@Getter
@Setter
@Accessors( chain= true)

@Entity
@DiscriminatorValue( "C")
class CashAccountTemplate extends PaymentTemplate {
    @Override public <T> T accept( TemplateVisitor<T> visitor) {
        return visitor.visit( this);
    }
}


@Getter
@Setter
@Accessors( chain= true)

@Entity
@DiscriminatorValue( "S")
class SalesInvoiceTemplate extends Template {

    @Override public <T> T accept( TemplateVisitor<T> visitor) {
        return visitor.visit( this);
    }
}