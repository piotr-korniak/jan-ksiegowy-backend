package pl.janksiegowy.backend.statement;

import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.pattern.PatternCode;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.util.Optional;

public interface StatementQueryRepository {
   //Optional<StatementDto> findByPatternIdAndPeriodId( PatternCode patternCode, String periodId);

    <StatementDto> Optional<StatementDto>  findFirstByPatternIdAndPeriodOrderByNoDesc(
            PatternId patternId, Period period);
}
