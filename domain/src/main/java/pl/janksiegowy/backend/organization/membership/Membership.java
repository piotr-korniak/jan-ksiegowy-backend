package pl.janksiegowy.backend.organization.membership;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.organization.tenant.Tenant;
import pl.janksiegowy.backend.organization.user.User;

import java.util.UUID;

@Setter
@Getter
@Accessors( chain= true)

@Entity
@Table( name= "MEMBERSHIPS")
public class Membership {

    @Id
    @GeneratedValue( strategy= GenerationType.UUID)
    private UUID id;

    @Column( name= "user_id", nullable= false)
    private UUID userId;

    @ManyToOne( fetch= FetchType.LAZY)
    @JoinColumn( name = "user_id", insertable= false, updatable= false)
    private User user;

    @Column( name= "tenant_id", nullable= false)
    private UUID tenantId;

    @ManyToOne( fetch= FetchType.EAGER)
    @JoinColumn( name= "tenant_id", insertable= false, updatable= false)
    private Tenant tenant;

    private String companyCode;
    private String companyName;

    @Enumerated( EnumType.STRING)
    private MembershipRole role;

}
