package pl.janksiegowy.backend.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.authorization.user.User;
import pl.janksiegowy.backend.user.dto.UserDto;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UserFactory {

    User from(UserDto source) {

        return new User()
                .setUserId( Optional.ofNullable( source.getUserId())
                        .orElseGet( UUID::randomUUID))
                .setUsername( source.getUsername())
                .setPassword( source.getPassword());
    }
}
