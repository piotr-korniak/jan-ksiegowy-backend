package pl.janksiegowy.backend.organization.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.organization.command.*;
import pl.janksiegowy.backend.organization.company.CompanyFacade;
import pl.janksiegowy.backend.organization.membership.MembershipFacade;
import pl.janksiegowy.backend.organization.membership.MembershipQueryRepository;
import pl.janksiegowy.backend.organization.membership.MembershipRole;
import pl.janksiegowy.backend.organization.port.CompanyRegistryLookupPort;
import pl.janksiegowy.backend.organization.port.TenantContextPort;
import pl.janksiegowy.backend.organization.tenant.TenantFacade;
import pl.janksiegowy.backend.organization.user.UserFacade;
import pl.janksiegowy.backend.organization.user.dto.UserDto;

@Service
@AllArgsConstructor
public class RegisterTenantWithInitialCompanyUseCase {

    private final TenantFacade tenantFacade;
    private final CompanyFacade companyFacade;
    private final UserFacade userFacade;
    private final MembershipFacade membershipFacade;

    private final CompanyRegistryLookupPort companyRegistryLookup;
    private final TenantContextPort tenantContextPort;

    private final MembershipQueryRepository memberships;

    //@Transactional
    public String handle( RegisterTenantWithInitialCompanyCommand command) {

        var entity= companyRegistryLookup.findByNip( command.nip());
        var tenant= tenantFacade.handle( CreateTenantCommand.builder()
                .name( entity.companyName())
                .code( command.code())
                .build());

        var company= tenantContextPort.runInTenant( tenant.getCode(), ()->
                companyFacade.handle( CreateCompanyCommand.builder()
                        .code( "pl"+ command.nip())
                        .name( entity.companyName())
                        .build())
        );

        var user= userFacade.handle( CreateUserCommand.builder()
                .username( command.email())
                .password( command.password())
                .tenantId( tenant.getId())
                .build());

        var membership= membershipFacade.handle( CreateMembershipCommand.builder()
                .tenantId( tenant.getId())
                .companyCode( company.getCode())
                .userId( user.getUserId())
                .role( MembershipRole.M)
                .build());

        if( user instanceof UserDto.Proxy userDto) {
            userFacade.save( userDto.lastMembershipId( membership.getId()));
        }

        System.out.println( "RegisterTenantWithInitialCompanyUseCase.handle: " + command);
        return "";
    }
}
