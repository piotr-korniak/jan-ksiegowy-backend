package pl.janksiegowy.backend.organization.membership;

import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.organization.command.CreateMembershipCommand;
import pl.janksiegowy.backend.organization.membership.dto.MembershipDto;

@Component
public class MembershipMapper {

    public Membership from( CreateMembershipCommand command) {
        return new Membership()
                .setUserId( command.userId())
                .setTenantId( command.tenantId())
                .setCompanyCode( command.companyCode())
                .setRole( command.role());
    }

    public MembershipDto toDto( Membership source) {
        return MembershipDto.create()
                .id( source.getId());
    }
}
