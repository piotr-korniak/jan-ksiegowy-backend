package pl.janksiegowy.backend.authorization.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.janksiegowy.backend.authorization.auth.dto.CompanyOptionDto;
import pl.janksiegowy.backend.authorization.auth.dto.LoginRequestDto;
import pl.janksiegowy.backend.authorization.auth.dto.LoginResponseDto;
import pl.janksiegowy.backend.authorization.auth.dto.UserSummaryDto;
import pl.janksiegowy.backend.organization.membership.MembershipQueryRepository;
import pl.janksiegowy.backend.organization.membership.MembershipRole;
import pl.janksiegowy.backend.organization.membership.dto.MembershipDto;
import pl.janksiegowy.backend.organization.tenant.TenantQueryRepository;
import pl.janksiegowy.backend.organization.user.UserQueryRepository;
import pl.janksiegowy.backend.organization.user.dto.UserDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v2/auth")
@AllArgsConstructor
public class AuthController {

    private final UserQueryRepository users;
    private final MembershipQueryRepository memberships;
    private final TenantQueryRepository tenants;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestBody LoginRequestDto payload) {
        String username= payload.email();
        String password= payload.password();

        System.out.println( "username: "+ username);

        var user= users.findByUsername( username)
                .orElseThrow(()-> new ResponseStatusException( HttpStatus.UNAUTHORIZED));

        List<MembershipDto> userMemberships= memberships.findAllByUserId( user.getUserId());

        if( userMemberships.isEmpty()) {
            throw new ResponseStatusException( HttpStatus.UNAUTHORIZED);
        }

        if( userMemberships.size()== 1) {
            return ResponseEntity.ok( buildLoggedInResponse( user, userMemberships.getFirst()));
        }

        if( user.getLastMembershipId()!= null) {
            return ResponseEntity.ok( 
                    buildLoggedInResponse( user, userMemberships.stream()
                            .filter(m-> user.getLastMembershipId().equals( m.getId()))
                            .findFirst()
                            .orElse( userMemberships.getFirst())));
        }

        return ResponseEntity.ok( new LoginResponseDto(
                true,
                true,
                null,
                null,
                buildUserSummary( user, null),
                userMemberships.stream()
                        .map( this::buildCompanyOption)
                        .toList()));
    }

    private Object buildLoggedInResponse( UserDto user, MembershipDto membership) {

        System.out.println( "buildLoggedInResponse: "+ membership.getTenantName());

        return new LoginResponseDto(
                true,
                false,
                jwtService.generateAccessToken(
                        user.getUserId(),
                        membership.getTenantCode(),
                        membership.getCompanyCode()
                ),
                jwtService.generateRefreshToken( user.getUserId()),
                buildUserSummary( user, membership.getRole()),
                List.of( buildCompanyOption( membership)));
    }

    private CompanyOptionDto buildCompanyOption( MembershipDto membership) {
        return new CompanyOptionDto(
                membership.getId(),
                membership.getCompanyCode(),
                membership.getCompanyName()
        );
    }

    private UserSummaryDto buildUserSummary( UserDto user, MembershipRole role) {
        return new UserSummaryDto(
                user.getUserId(),
                user.getUsername(),
                Stream.of( user.getFirstName(), user.getLastName())
                        .filter( Objects::nonNull)
                        .map( String::trim)
                        .filter(s-> !s.isEmpty())
                        .collect( Collectors.joining(" ")),
                role
        );
    }
}
