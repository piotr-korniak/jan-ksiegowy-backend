package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.statement.dto.StatementDto;

@AllArgsConstructor
public class StatementFacade {

    private final StatementFactory factory;
    private final StatementRepository repository;

    private final DecreeFacade decrees;

    public Statement save( StatementDto source) {
        return repository.save( factory.from( source));
    }

    public void approve( Statement statement) {
        decrees.book( statement);
    }
}
