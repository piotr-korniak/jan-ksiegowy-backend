package pl.janksiegowy.backend.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.tenant.dto.TenantDto;

import java.util.Optional;
import java.util.UUID;

public interface SqlTenantRepository extends JpaRepository<Tenant, UUID> {
    boolean existsByCode( String code);

}

@org.springframework.stereotype.Repository
interface SqlTenantQueryRepository extends TenantQueryRepository, Repository<Tenant, UUID> {

    Optional<TenantDto> findByCode( String code);
}
