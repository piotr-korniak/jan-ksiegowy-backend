package pl.janksiegowy.backend.statement;

import pl.janksiegowy.backend.statement.dto.StatementLineDto;

public class StatementLineFactory {

    public StatementLine from( StatementLineDto statementLineDto) {
        return new StatementLine()
                .setItemCode( statementLineDto.getItemCode())
                .setAmount( statementLineDto.getAmount());
    }
}
