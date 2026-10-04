package pl.janksiegowy.backend.organization.membership;

import java.util.Optional;
import java.util.UUID;

public interface MembershipRepository {

    Optional<Membership> findById( UUID id );
    Membership save( Membership membership);
}
