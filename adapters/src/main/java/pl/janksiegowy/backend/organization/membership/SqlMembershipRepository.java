package pl.janksiegowy.backend.organization.membership;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface SqlMembershipRepository extends JpaRepository<Membership, UUID> {

}

interface SqlMembershipQueryRepository extends MembershipQueryRepository, Repository<Membership, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class MembershipRepositoryImpl implements MembershipRepository {

    private final SqlMembershipRepository repository;

    @Override
    public Optional<Membership> findById( UUID id) {
        return repository.findById( id);
    }

    @Override public Membership save( Membership membership) {
        return repository.save( membership);
    }
}
