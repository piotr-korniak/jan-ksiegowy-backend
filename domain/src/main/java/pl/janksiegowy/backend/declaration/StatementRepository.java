package pl.janksiegowy.backend.declaration;

import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.pattern.PatternId;

import java.util.Optional;

public interface StatementRepository {

    Declaration save(Declaration statement);

    Optional<Declaration> findByPatternIdAndPeriod(PatternId patternId, Period period);
    Optional<Declaration> findFirstByPatternLikeAndPeriodOrderByNoDesc(String pattern, Period period);
}
