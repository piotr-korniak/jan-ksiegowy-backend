package pl.janksiegowy.backend.organization.membership;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.organization.command.CreateMembershipCommand;
import pl.janksiegowy.backend.organization.membership.dto.MembershipDto;

@Service
@AllArgsConstructor
public class MembershipFacade {

    private final MembershipRepository repository;
    private final MembershipQueryRepository memberships;

    private final MembershipMapper mapper;

    public MembershipDto handle( CreateMembershipCommand command) {
        return memberships
                .findByUserIdAndTenantIdAndCompanyCode( command.userId(), command.tenantId(), command.companyCode())
                .orElseGet(()-> mapper.toDto( repository.save( mapper.from( command))));
    }
}
