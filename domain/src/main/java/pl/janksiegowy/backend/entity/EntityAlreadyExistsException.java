package pl.janksiegowy.backend.entity;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException( String message) {
        super( message);
    }
}
