package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.settlement.StatementSettlement;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class StatementFactory {
    private final EntityRepository entities;
    private final PeriodRepository periods;

    public Statement from( StatementDto source) {
        var period= periods.findById( source.getPeriodId()).orElseThrow();
        return Optional.ofNullable( source.getSettlementCt())
                .map( liability-> new Statement()
                    .setSettlement( (StatementSettlement)new StatementSettlement()
                        .setDate( source.getDate())
                        .setNumber( source.getSettlementNumber())
                        .setEntity( entities.findByEntityIdAndDate(
                                source.getSettlementEntity().getEntityId(), source.getDate()).orElseThrow())
                        .setDue( source.getSettlementDue())
                        .setCt( liability)
                        .setPeriod(  periods.findMonthByDate( period.getEnd()).orElseThrow())))
                .orElse(  new Statement())
                .setPattern( source.getPatternId())
                .setDate( source.getDate())
                .setCreated( source.getCreated())
                .setPeriod( period)
                .setStatementId( Optional.ofNullable( source.getStatementId())
                        .orElse( UUID.randomUUID()));

    }
}
