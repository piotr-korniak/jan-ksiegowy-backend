package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.register.invoice.InvoiceRegister;
import pl.janksiegowy.backend.shared.pattern.PatternCode;

import java.util.Optional;
import java.util.UUID;

public interface SqlStatementRepository extends JpaRepository<Statement, UUID> {
    Optional<Statement> findByPatternIdAndPeriod( PatternCode patternCode, Period period);
}

interface SqlQuery extends StatementQueryRepository, Repository<Statement, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class StatementRepositoryImpl implements StatementRepository {

    private final SqlStatementRepository repository;
    @Override public Statement save( Statement statement) {
        return repository.save( statement);
    }

    @Override
    public Optional<Statement> findByPatternIdAndPeriod( PatternCode patternCode, Period period) {
        return repository.findByPatternIdAndPeriod( patternCode, period);
    }
}