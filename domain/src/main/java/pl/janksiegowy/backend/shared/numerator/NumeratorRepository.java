package pl.janksiegowy.backend.shared.numerator;

import java.util.Optional;

public interface NumeratorRepository {

    Numerator save( Numerator numerator);
    Optional<Numerator> findByCode( String code);
}
