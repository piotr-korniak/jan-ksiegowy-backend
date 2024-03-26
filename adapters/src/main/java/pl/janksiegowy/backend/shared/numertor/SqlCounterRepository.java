package pl.janksiegowy.backend.shared.numertor;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.janksiegowy.backend.shared.numerator.Counter;
import pl.janksiegowy.backend.shared.numerator.CounterRepository;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorRepository;

import java.util.Optional;

public interface SqlCounterRepository extends JpaRepository<Counter, Long> {
    Optional<Counter> findByNumeratorCode( String code);
    Optional<Counter> findByNumeratorCodeAndYear( String code, int year);
    Optional<Counter> findByNumeratorCodeAndMonth( String code, int month);

    Optional<Counter> findByNumeratorCodeAndType( NumeratorCode code, String type);
    Optional<Counter> findByNumeratorCodeAndYearAndType( NumeratorCode code, int year, String type);
    Optional<Counter> findByNumeratorCodeAndYearAndMonthAndType( NumeratorCode code, int year,
                                                                 int month, String type );
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class CounterRepositoryImpl implements CounterRepository {

    private final SqlCounterRepository repository;
    @Override public Counter save( Counter counter) {
        return repository.save( counter);
    }

    @Override public Optional<Counter> findByNumeratorCode( String code) {
        return repository.findByNumeratorCode( code);
    }
    @Override public Optional<Counter> findByNumeratorCodeAndYear( String code, int year) {
        return repository.findByNumeratorCodeAndYear( code, year);
    }
    @Override public Optional<Counter> findByNumeratorCodeAndMonth( String code, int month) {
        return repository.findByNumeratorCodeAndMonth( code, month);
    }

    @Override public Optional<Counter> findByNumeratorCodeAndType( NumeratorCode code, String type) {
        return repository.findByNumeratorCodeAndType( code, type);
    }
    @Override public Optional<Counter> findByNumeratorCodeAndYearAndType( NumeratorCode code, int year, String type) {
        return repository.findByNumeratorCodeAndYearAndType( code, year, type);
    }

    @Override public Optional<Counter> findByNumeratorCodeAndYearAndMonthAndType( NumeratorCode code,
                                                                                  int year,
                                                                                  int month,
                                                                                  String type) {
        return repository.findByNumeratorCodeAndYearAndMonthAndType( code, year, month, type);
    }
}