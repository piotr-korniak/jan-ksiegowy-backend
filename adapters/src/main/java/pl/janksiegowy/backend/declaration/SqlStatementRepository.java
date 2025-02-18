package pl.janksiegowy.backend.declaration;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.pattern.PatternId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface SqlStatementRepository extends JpaRepository<Declaration, UUID> {
    Optional<Declaration> findByPatternIdAndPeriod(PatternId patternId, Period period);
    Optional<Declaration> findFirstByPatternLikeAndPeriodOrderByNoDesc(String pattern, Period period);
}

interface SqlStatementQueryRepository extends StatementQueryRepository, Repository<Declaration, UUID> {
    @Override
    @Query( "SELECT coalesce( sum( c.amount), 0) FROM PayableDeclaration d " +
            "LEFT JOIN Clearing c ON d.statementId= c.payableId " +
            "WHERE TYPE(d)= :type AND d.settlementPeriod= :month AND c.date <= :date")
    BigDecimal sumByTypeAndPeriodAndDueDate( Class<? extends PayableDeclaration> type,
                                             MonthPeriod month, LocalDate date);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class StatementRepositoryImpl implements StatementRepository {

    private final SqlStatementRepository repository;
    @Override public Declaration save(Declaration statement) {
        return repository.save( statement);
    }

    @Override
    public Optional<Declaration> findByPatternIdAndPeriod(PatternId patternId, Period period) {
        return repository.findByPatternIdAndPeriod( patternId, period);
    }

    @Override
    public Optional<Declaration> findFirstByPatternLikeAndPeriodOrderByNoDesc(String pattern, Period period) {
        return repository.findFirstByPatternLikeAndPeriodOrderByNoDesc( pattern, period);
    }
}