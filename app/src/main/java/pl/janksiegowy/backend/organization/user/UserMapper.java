package pl.janksiegowy.backend.organization.user;

import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.organization.command.CreateUserCommand;
import pl.janksiegowy.backend.organization.user.dto.UserDto;

import java.util.Optional;
import java.util.UUID;

@Component
class UserMapper {

    User from( CreateUserCommand source) {
        return new User()
                .setUserId( UUID.randomUUID())
                .setUsername( source.username())
                .setPassword( source.password())
                .setTenantId( source.tenantId());
    }

    User from( UserDto source) {
        return new User()
                .setUserId( Optional.ofNullable( source.getUserId())
                        .orElseGet( UUID::randomUUID))
                .setUsername( source.getUsername())
                .setPassword( source.getPassword())
                .setTenantId( source.getTenantId())
                .setLastMembershipId( source.getLastMembershipId());
    }

    UserDto toDto( User source) {
        return UserDto.create()
                .userId( source.getUserId())
                .username( source.getUsername())
                .password( source.getPassword())
                .tenantId( source.getTenantId())
                .lastMembershipId( source.getLastMembershipId());
    }
}
