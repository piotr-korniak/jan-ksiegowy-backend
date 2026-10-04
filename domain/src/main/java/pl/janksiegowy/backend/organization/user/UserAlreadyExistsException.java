package pl.janksiegowy.backend.organization.user;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException( String message) {
        super( message);
    }
}

