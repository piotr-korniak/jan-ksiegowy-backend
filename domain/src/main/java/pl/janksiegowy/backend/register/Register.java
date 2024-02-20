package pl.janksiegowy.backend.register;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter

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

    public Register setRegisterId( UUID registerId){
        this.registerId= registerId;
        return this;
    }

    public Register setCode( String code) {
        this.code= code;
        return this;
    }
    public Register setName( String name) {
        this.name= name;
        return this;
    }

}
