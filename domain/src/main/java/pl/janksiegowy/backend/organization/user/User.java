package pl.janksiegowy.backend.organization.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.organization.membership.Membership;
import pl.janksiegowy.backend.organization.tenant.Tenant;

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

    private String firstName;
    private String lastName;

    @Column( name= "tenant_id", nullable= false)
    private UUID tenantId;

    @ManyToOne( fetch= FetchType.LAZY)
    @JoinColumn( name= "tenant_id", insertable= false, updatable= false)
    private Tenant tenant;

    @Column( name= "last_membership_id", nullable= false)
    private UUID lastMembershipId;

    @ManyToOne( fetch= FetchType.LAZY)
    @JoinColumn( name= "last_membership_id", insertable= false, updatable= false)
    private Membership lastMembership;
}
