package pl.janksiegowy.backend.accounting.template;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.register.accounting.AccountingRegister;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "TEMPLATES")
public class Template {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private long id;
    private UUID templateId;
    private LocalDate date;

    private String code;
    private String name;

    @ManyToOne
    private AccountingRegister register;

    @Enumerated( EnumType.STRING)
    private DocumentType documentType;



    @OneToMany( fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval= true)
    @OrderColumn( name= "NO")
    @JoinColumn( name= "TEMPLATE_ID")
    private List<TemplateLine> items;

}
