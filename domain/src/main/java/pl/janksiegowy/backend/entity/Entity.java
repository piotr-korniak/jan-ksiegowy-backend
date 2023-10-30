package pl.janksiegowy.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Table( name= "ENTITIES")
@jakarta.persistence.Entity
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
abstract class Entity {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private long id;

    @UuidGenerator
    private UUID entityId; // for history and query

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private EntityType type;

    private LocalDate date;
    @Column( name= "TAX_NUMBER")
    private String taxNumber;

    private String name;
    private String address;
    private String postcode;
    private String town;

    @Enumerated( EnumType.STRING)
    private Country country;

    public boolean isSupplier() {
        return false;
    }
    public Entity setSupplier( boolean supplier) {
        return this;
    };

    public boolean isCustomer() {
        return false;
    }
    public Entity setCustomer( boolean customer) {
        return this;
    };
}

@Getter
@Setter
@Accessors( chain= true)

@jakarta.persistence.Entity
@DiscriminatorValue( value= "C")
class Contact extends Entity {
    private boolean supplier;
    private boolean customer;
}
