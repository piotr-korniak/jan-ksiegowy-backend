package pl.janksiegowy.backend.declaration;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.pattern.PatternId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface StatementQueryRepository {
   //Optional<StatementDto> findByPatternIdAndPeriodId( PatternCode patternCode, String periodId);

    <StatementDto> Optional<StatementDto>  findFirstByPatternIdAndPeriodOrderByNoDesc(
            PatternId patternId, Period period);

    BigDecimal sumByTypeAndPeriodAndDueDate( Class<? extends PayableDeclaration> declarationDraClass,
                                             MonthPeriod month, LocalDate localDate);
}
