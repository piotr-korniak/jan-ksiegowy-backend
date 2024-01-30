package pl.janksiegowy.backend.statement;

import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.pattern.PatternCode;

import java.util.Optional;

public interface StatementRepository {

    Statement save( Statement statement);

    Optional<Statement> findByPatternIdAndPeriod( PatternCode patternCode, Period period);
}
