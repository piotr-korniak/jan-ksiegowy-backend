package pl.janksiegowy.backend.organization.user;


import pl.janksiegowy.backend.organization.user.dto.UserDto;

import java.util.Optional;

public interface UserQueryRepository {

    boolean existsByUsername( String username);
    Optional<UserDto> findByUsername( String username);
}
