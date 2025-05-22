package pl.janksiegowy.backend.user;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.authorization.user.User;
import pl.janksiegowy.backend.authorization.user.UserRepository;
import pl.janksiegowy.backend.user.dto.UserDto;

@AllArgsConstructor
public class UserFacade {

    private final UserRepository repository;
    private final UserFactory factory;


    public User save( final UserDto source) {
        return repository.save( factory.from( source));
    }
}
