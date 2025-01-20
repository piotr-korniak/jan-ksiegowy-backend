package pl.janksiegowy.backend.report;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "REPORT_SCHEMAS")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING)
abstract class ReportSchema {

    @Id
    @Column( name= "ID")
    private UUID reportSchemaId;
    @Column( name= "PARENT_ID")
    private UUID parentSchemaId;

    private String code;
    private String name;

    @Enumerated( EnumType.STRING)
    private ReportFunction function;
    private String parameters;

    @OneToMany( fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval= true)
    @OrderColumn( name= "NO")
    @JoinColumn( name= "PARENT_ID", referencedColumnName= "ID")
    private List<ReportSchema> items= new ArrayList<>();

    private int no;
    private boolean hidden;

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private ReportType type;

    public ReportType getType() {
        return ReportType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }
}

@Entity
@DiscriminatorValue( value= "B")
class BalanceReport extends ReportSchema {

}

@Entity
@DiscriminatorValue( value= "C")
class CITReport extends ReportSchema {

}

@Entity
@DiscriminatorValue( value= "P")
class ProfitAndLossReport extends ReportSchema {

}

