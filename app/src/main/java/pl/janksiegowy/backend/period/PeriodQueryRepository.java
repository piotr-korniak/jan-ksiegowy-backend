package pl.janksiegowy.backend.period;

import pl.janksiegowy.backend.period.dto.PeriodDto;

import java.time.LocalDate;
import java.util.Optional;

public interface PeriodQueryRepository {

    default Optional<PeriodDto> findMonthByDate( LocalDate date){   // alias
        return findAllByBeginLessThanEqualAndEndGreaterThanEqualAndType( date, date, PeriodType.M);
    };

    Optional<PeriodDto> findAllByBeginLessThanEqualAndEndGreaterThanEqualAndType(
            LocalDate begin, LocalDate end, PeriodType type);
}
