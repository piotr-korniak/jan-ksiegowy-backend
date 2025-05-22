package pl.janksiegowy.backend.authorization.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.user.UserFacade;
import pl.janksiegowy.backend.user.UserFactory;

@Configuration
public class UserConfiguration {

    @Bean
    UserFacade userFacade(final UserRepository repository) {
        return new UserFacade( repository, new UserFactory());
    }
}
