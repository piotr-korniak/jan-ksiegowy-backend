package pl.janksiegowy.backend.company;

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
@Table( name= "COMPANIES" )
public class Company {
    @Id
    @UuidGenerator
    private UUID id;

    private String code;
    private String name;

    public String getCode() {
        return code;
    }
}
