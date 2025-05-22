package pl.janksiegowy.backend.user;

public interface UserQueryRepository {

    boolean existsByUsername( String username);
}
