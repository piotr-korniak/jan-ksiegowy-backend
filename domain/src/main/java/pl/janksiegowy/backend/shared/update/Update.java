package pl.janksiegowy.backend.shared.update;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Table( name= "UPDATES")
@Setter
@Accessors( chain= true)
@Getter
public class Update {
    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private Long id;

    @Column( nullable= false)
    private String stepUrl;

    private String params;

    private LocalDateTime executedAt;
}
