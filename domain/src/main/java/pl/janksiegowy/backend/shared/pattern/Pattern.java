package pl.janksiegowy.backend.shared.pattern;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Setter
@Accessors( chain= true)

@Entity
@Table( name= "JAN.PATTERNS")
public class Pattern {

    @Id
    @Enumerated( EnumType.STRING)
    private PatternId id;

    @Enumerated( EnumType.STRING)
    private PatternCode patternId;

    private LocalDate date;
    private String name;

    private String xsd;

}
