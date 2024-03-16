package pl.janksiegowy.backend.shared.numerator;

public interface NumeratorQueryRepository {

    boolean existsByCode( NumeratorCode code);
}
