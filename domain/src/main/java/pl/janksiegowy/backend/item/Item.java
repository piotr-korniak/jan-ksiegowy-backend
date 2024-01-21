package pl.janksiegowy.backend.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.financial.TaxMetod;
import pl.janksiegowy.backend.financial.TaxRate;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "ITEMS")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
public class Item {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private long id;
    private UUID itemId;
    private LocalDate date;

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private ItemType type;

    @Enumerated( EnumType.STRING)
    private TaxMetod taxMetod;

    @Enumerated( EnumType.STRING)
    private TaxRate taxRate;

    private String code;
    private String name;
    private String measure;

    private Boolean sold;
    private Boolean purchased;

}

@Entity
@DiscriminatorValue( value= "A")
class Asset extends Item {

}

@Entity
@DiscriminatorValue( value= "M")
class Material extends Item {

}

@Entity
@DiscriminatorValue( value= "S")
class Service extends Item {

}


@Entity
@DiscriminatorValue( value= "P")
class Product extends Item {

}