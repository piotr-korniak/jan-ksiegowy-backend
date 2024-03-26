package pl.janksiegowy.backend.shared.numerator;

import java.util.Optional;

public interface CounterRepository {

    Counter save( Counter counter);
    Optional<Counter> findByNumeratorCode( String code);
    Optional<Counter> findByNumeratorCodeAndYear( String code, int year);
    Optional<Counter> findByNumeratorCodeAndMonth( String code, int month);

    Optional<Counter> findByNumeratorCodeAndType( NumeratorCode code, String type );
    Optional<Counter> findByNumeratorCodeAndYearAndType( NumeratorCode code, int year, String type);
    Optional<Counter> findByNumeratorCodeAndYearAndMonthAndType( NumeratorCode code, int year,
                                                                 int month, String type);

}
