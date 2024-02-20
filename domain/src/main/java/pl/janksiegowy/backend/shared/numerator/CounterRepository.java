package pl.janksiegowy.backend.shared.numerator;

import java.util.Optional;

public interface CounterRepository {

    Counter save( Counter counter);
    Optional<Counter> findByNumeratorCode( String code);
    Optional<Counter> findByNumeratorCodeAndYear( String code, int year);
    Optional<Counter> findByNumeratorCodeAndMonth( String code, int month);

    Optional<Counter> findByNumeratorCodeAndType( String code, String type );
    Optional<Counter> findByNumeratorCodeAndYearAndType( String code, int year, String type);
    Optional<Counter> findByNumeratorCodeAndMonthAndType( String code, int month, String type);

}
