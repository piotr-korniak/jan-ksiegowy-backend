package pl.janksiegowy.backend.period;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.Optional;

public interface SqlPeriodRepository extends JpaRepository<Period, String> {
    Period findAllByBeginLessThanEqualAndEndGreaterThanEqualAndType(
            LocalDate begin, LocalDate end, PeriodType type);

    Period findAllByIdAndType( String id, PeriodType periodType);
}

interface SqlPeriodQueryRepository extends PeriodQueryRepository, Repository<Period, String> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class PeriodRepositoryImpl implements PeriodRepository {

    private final SqlPeriodRepository repository;

    @Override public Optional<MonthPeriod> findMonthById( String id) {
        return Optional.ofNullable( (MonthPeriod)repository
                .findAllByIdAndType( id, PeriodType.M));
    }

    @Override public Optional<MonthPeriod> findMonthByDate( LocalDate date) {
        return Optional.ofNullable( (MonthPeriod)repository.
                findAllByBeginLessThanEqualAndEndGreaterThanEqualAndType( date, date, PeriodType.M));
    }
    @Override public Optional<QuaterPeriod> findQuarterByDate(LocalDate date) {
        return Optional.ofNullable( (QuaterPeriod)repository.
                findAllByBeginLessThanEqualAndEndGreaterThanEqualAndType( date, date, PeriodType.Q));
    }
    @Override public Optional<AnnualPeriod> findAnnualByDate( LocalDate date) {
        return Optional.ofNullable( (AnnualPeriod)repository.
                findAllByBeginLessThanEqualAndEndGreaterThanEqualAndType( date, date, PeriodType.A));
    }

    @Override
    public Period save( Period period) {
        return repository.save( period);
    }
}
