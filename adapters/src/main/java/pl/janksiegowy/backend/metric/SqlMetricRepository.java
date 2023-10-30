package pl.janksiegowy.backend.metric;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;


public interface SqlMetricRepository extends JpaRepository<Metric, LocalDate> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class MetricRepositoryImpl implements MetricRepository {

    private final SqlMetricRepository repository;

    @Override
    public Metric save( Metric entity) {
        return repository.save( entity);
    }
}


