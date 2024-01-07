package pl.janksiegowy.backend.metric;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface SqlMetricRepository extends JpaRepository<Metric, LocalDate> {

    @Query( value= "FROM Metric M " +
            "LEFT OUTER JOIN Metric P " +
            "ON P.id <= :date AND M.id < P.id " +
            "WHERE M.id <= :date AND P.id IS NULL")
    Optional<Metric> findByDate( LocalDate date);
    Optional<Metric> findFirstByOrderById();

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class MetricRepositoryImpl implements MetricRepository {

    private final SqlMetricRepository repository;

    @Override
    public Metric save( Metric entity) {
        return repository.save( entity);
    }

    @Override public Optional<Metric> findByDate( LocalDate date) {
        return repository.findByDate( date);
    }

    @Override
    public boolean existById( LocalDate id) {
        return repository.existsById( id);
    }

    @Override
    public Optional<Metric> findFirstByOrderById() {
        return repository.findFirstByOrderById();
    }


}


