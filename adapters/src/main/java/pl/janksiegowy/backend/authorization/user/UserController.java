package pl.janksiegowy.backend.authorization.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.subdomain.DomainController;
import pl.janksiegowy.backend.user.UserFacade;
import pl.janksiegowy.backend.user.UserQueryRepository;
import pl.janksiegowy.backend.user.dto.UserDto;

import java.util.Map;

@DomainController
@RequestMapping( "/v2/users")
@AllArgsConstructor
public class UserController {

    private final UserQueryRepository users;
    private final UserFacade userFacade;

    @PostMapping("/register")
    public ResponseEntity<String> register( @RequestBody Map<String, String> payload) {
        String username= payload.get( "username");
        String rawPassword= payload.get( "password");

        System.err.println( "Username: "+ username);
        System.err.println( "Password: "+ rawPassword);

        if( users.existsByUsername( username))
            return ResponseEntity.badRequest().body( "User already exists");

        userFacade.save( UserDto.create()
                .username( username)
                .password( rawPassword));

        return ResponseEntity.ok("User registered");
    }
}