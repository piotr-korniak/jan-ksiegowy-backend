package pl.janksiegowy.backend.organization.membership;

import pl.janksiegowy.backend.organization.membership.dto.MembershipDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MembershipQueryRepository {

    List<MembershipDto> findAllByUserId( UUID userId);
    Optional<MembershipDto> findByUserIdAndTenantIdAndCompanyCode( UUID userId, UUID tenantId, String companyCode);

    <T> Optional<T> findById( UUID id, Class<T> type);
    default Optional<MembershipDto> findById( UUID id) {
        return findById( id, MembershipDto.class);
    }


}
