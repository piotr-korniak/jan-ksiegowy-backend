package pl.janksiegowy.backend.authorization.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.authorization.tenant.Tenant;

import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "USERS")
public class User {

    @Id
    @Column( name= "ID")
    private UUID userId;

    private String username;
    private String password;

    @ManyToOne
    private Tenant tenant;


}
