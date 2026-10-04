package pl.janksiegowy.backend.organization.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping( "/v2/users")
@AllArgsConstructor
public class UserController {

//    private final UserQueryRepository users;
//    private final UserFacade userFacade;

    @PostMapping("/register")
    public ResponseEntity<String> register( @RequestBody Map<String, String> payload) {
        String username= payload.get( "username");
        String rawPassword= payload.get( "password");

        System.err.println( "Username: "+ username);
        System.err.println( "Password: "+ rawPassword);
/*
        if( users.existsByUsername( username))
            return ResponseEntity.badRequest().body( "User already exists");

        userFacade.save( UserDto.create()
                .username( username)
                .password( rawPassword));
*/
        return ResponseEntity.ok("User registered");
    }
}