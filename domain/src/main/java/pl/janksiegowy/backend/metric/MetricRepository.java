package pl.janksiegowy.backend.metric;

import java.time.LocalDate;
import java.util.Optional;

public interface MetricRepository {

    Metric save( Metric entity);
    Optional<Metric> findByDate( LocalDate date);
    boolean existById( LocalDate id);
    Optional<Metric> findFirstByOrderById();
}
