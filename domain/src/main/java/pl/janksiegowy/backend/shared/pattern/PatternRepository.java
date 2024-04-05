package pl.janksiegowy.backend.shared.pattern;

import java.time.LocalDate;
import java.util.Optional;

public interface PatternRepository {

    public Pattern save( Pattern pattern);
    Optional<PatternId> dupa( LocalDate date );
}
