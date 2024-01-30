package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.statement.dto.StatementDto;

@AllArgsConstructor
public class StatementFacade {

    private final StatementFactory factory;
    private final StatementRepository repository;

    public Statement save( StatementDto source) {
        return repository.save( factory.from( source));
    }
}
