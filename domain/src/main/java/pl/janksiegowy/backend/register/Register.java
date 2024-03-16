package pl.janksiegowy.backend.register;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "REGISTERS")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class Register {

    @Id
    @UuidGenerator
    @Column( name= "ID")
    private UUID registerId;

    private String code;
    private String name;

    private String accountNumber= "";

}
