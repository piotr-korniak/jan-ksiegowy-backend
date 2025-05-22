package pl.janksiegowy.backend.authorization.tenant;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.tenant.TenantQueryRepository;

import java.util.Optional;
import java.util.UUID;

public interface SqlTenantRepository extends JpaRepository<Tenant, UUID> {

    boolean existsByCode( String code);
    Optional<Tenant> findByCode( String code);
}

interface SqlTenantQueryRepository extends TenantQueryRepository, Repository<Tenant, UUID> {

}


@org.springframework.stereotype.Repository
@AllArgsConstructor
class TenantRepositoryImpl implements TenantRepository {

    private final SqlTenantRepository repository;

    @Override
    public Optional<Tenant> findByCode( String code) {
        return repository.findByCode( code);
    }
}
