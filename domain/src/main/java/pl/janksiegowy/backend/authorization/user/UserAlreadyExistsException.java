package pl.janksiegowy.backend.authorization.user;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException( String message) {
        super( message);
    }
}
