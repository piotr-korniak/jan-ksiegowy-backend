package pl.janksiegowy.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import pl.janksiegowy.backend.finances.note.NoteType;

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

    public EntityType getType() {
        return EntityType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

    private LocalDate date;
    private String taxNumber;

    private String name;
    private String address;
    private String postcode;
    private String town;

    private String accountNumber;

    @Enumerated( EnumType.STRING)
    private Country country;

    private boolean supplier= false;
    private boolean customer= false;

}

@jakarta.persistence.Entity
@DiscriminatorValue( value= "B")
class Bank extends Entity {

}

@Getter
@Setter
@Accessors( chain= true)

@jakarta.persistence.Entity
@DiscriminatorValue( value= "C")
class Contact extends Entity {

}
@Getter
@Setter
@Accessors( chain= true)

@jakarta.persistence.Entity
@DiscriminatorValue( value= "R")
class Revenue extends Entity {

}

@Getter
@Setter
@Accessors( chain= true)

@jakarta.persistence.Entity
@DiscriminatorValue( value= "S")
class Shareholders extends Entity {

}

@Getter
@Setter
@Accessors( chain= true)

@jakarta.persistence.Entity
@DiscriminatorValue( value= "E")
class Employee extends Entity {

}
