package pl.janksiegowy.backend.statement;

import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.pattern.PatternCode;
import pl.janksiegowy.backend.shared.pattern.PatternId;

import java.util.Optional;

public interface StatementRepository {

    Statement save( Statement statement);

    Optional<Statement> findByPatternIdAndPeriod( PatternId patternId, Period period);
    Optional<Statement> findFirstByPatternIdAndPeriodOrderByNoDesc(  PatternId patternId, Period period);
}
