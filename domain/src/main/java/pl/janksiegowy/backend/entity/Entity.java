package pl.janksiegowy.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Table( name= "ENTITIES")
@jakarta.persistence.Entity
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class Entity {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private long id;

    private UUID entityId; // for history and query

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private EntityType type;

    public abstract EntityType getType();

    private LocalDate date;
    private String taxNumber;

    private String name;
    private String address;
    private String postalCode;
    private String city;

    private String accountNumber;

    @Enumerated( EnumType.STRING)
    private Country country;

    private boolean supplier= false;
    private boolean customer= false;

}

@Getter
@Setter
@Accessors( chain= true)

@jakarta.persistence.Entity
@DiscriminatorValue( value= "B")
class Bank extends Entity {

    @Override public EntityType getType() {
        return EntityType.B;
    }
}

@Getter
@Setter
@Accessors( chain= true)

@jakarta.persistence.Entity
@DiscriminatorValue( value= "C")
class Contact extends Entity {

    @Override public EntityType getType() {
        return EntityType.C;
    }
}

@Getter
@Setter
@Accessors( chain= true)

@jakarta.persistence.Entity
@DiscriminatorValue( value= "R")
class Revenue extends Entity {

    @Override public EntityType getType() {
        return EntityType.R;
    }
}

@Getter
@Setter
@Accessors( chain= true)

@jakarta.persistence.Entity
@DiscriminatorValue( value= "S")
class Shareholders extends Entity {

    @Override public EntityType getType() {
        return EntityType.S;
    }
}

@Getter
@Setter
@Accessors( chain= true)

@jakarta.persistence.Entity
@DiscriminatorValue( value= "E")
class Employee extends Entity {

    @Override public EntityType getType() {
        return EntityType.E;
    }
}
