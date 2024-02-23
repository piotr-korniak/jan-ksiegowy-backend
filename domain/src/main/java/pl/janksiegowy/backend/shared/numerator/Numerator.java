package pl.janksiegowy.backend.shared.numerator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors( chain= true)
@Getter

@Entity
@Table( name = "NUMERATORS")
public class Numerator {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private String mask;

    @Enumerated( EnumType.STRING)
    private NumeratorType type;
    private boolean typed;

}
