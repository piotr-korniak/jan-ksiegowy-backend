package pl.janksiegowy.backend.shared.numertor;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.shared.numerator.Numerator;
import pl.janksiegowy.backend.shared.numerator.NumeratorQueryRepository;
import pl.janksiegowy.backend.shared.numerator.NumeratorRepository;

import java.util.Optional;

public interface SqlNumeratorRepository extends JpaRepository<Numerator, Long> {
    Optional<Numerator> findByCode( String code );
}

interface SqlNumeratorQueryRepository extends NumeratorQueryRepository, Repository<Numerator, Long> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class NumeratorRepositoryImpl implements NumeratorRepository {

    private final SqlNumeratorRepository repository;
    @Override public Numerator save( Numerator numerator) {
        return repository.save( numerator);
    }

    @Override public Optional<Numerator> findByCode( String code) {
        return repository.findByCode( code);
    }
}