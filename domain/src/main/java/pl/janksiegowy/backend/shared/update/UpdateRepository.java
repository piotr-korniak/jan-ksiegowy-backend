package pl.janksiegowy.backend.shared.update;

public interface UpdateRepository {

    boolean existsByStepUrl( String stepUrl);
    Update save( Update update);
}
