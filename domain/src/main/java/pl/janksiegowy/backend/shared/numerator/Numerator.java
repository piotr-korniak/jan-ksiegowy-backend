package pl.janksiegowy.backend.shared.numerator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Setter
@Accessors( chain= true)
@Getter

@Entity
@Table( name = "NUMERATORS")
public class Numerator {

    @Id
    @UuidGenerator
    @Column( name= "ID")
    private UUID numeratorId;

    @Enumerated( EnumType.STRING)
    private NumeratorCode code;

    private String name;
    private String mask;

    @Enumerated( EnumType.STRING)
    private NumeratorType type;
    private boolean typed;

}
