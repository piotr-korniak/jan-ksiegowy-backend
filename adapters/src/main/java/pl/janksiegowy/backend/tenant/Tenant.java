package pl.janksiegowy.backend.tenant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;
@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "TENANTS")
public class Tenant {

    @Id
    @UuidGenerator
    private UUID id;

    private String code;
    private String name;
    private String password;
}
