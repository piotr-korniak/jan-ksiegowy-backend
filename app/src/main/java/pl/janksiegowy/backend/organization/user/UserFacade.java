package pl.janksiegowy.backend.organization.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.organization.command.CreateUserCommand;
import pl.janksiegowy.backend.organization.user.dto.UserDto;

@Service
@AllArgsConstructor
public class UserFacade {

    private final UserQueryRepository users;
    private final UserRepository repository;

    private final UserMapper mapper;


    public UserDto save( final UserDto source) {
        return mapper.toDto( repository.save( mapper.from( source)));
    }

    public UserDto handle( CreateUserCommand command) {
        return users.findByUsername( command.username())
                .orElseGet(()-> mapper.toDto( repository.save( mapper.from( command))));
    }
}
