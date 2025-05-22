package pl.janksiegowy.backend.authorization.user;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.authorization.tenant.TenantRepository;
import pl.janksiegowy.backend.database.TenantContext;
import pl.janksiegowy.backend.user.UserQueryRepository;

import java.util.UUID;

public interface SqlUserRepository extends JpaRepository<User, UUID> {
}

interface SqlUserQueryRepository extends UserQueryRepository, Repository<User, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class UserRepositoryImpl implements UserRepository {

    private final JpaRepository<User, UUID> repository;
    private final TenantRepository tenants;

    @Override
    public User save( User user) {
        return tenants.findByCode( TenantContext.getCurrentTenant().getTenant())
                .map( tenant-> repository
                        .save( user.setTenant( tenant)))
                .orElseThrow( () -> new IllegalStateException( "Tenant not found"));
    }
}