package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.statement.StatementKind.StatementKindVisitor;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class StatementFactory {
    private final EntityRepository entities;
    private final StatementLineFactory line;
    private final MetricRepository metrics;

    public Statement from( StatementDto source, MonthPeriod settlementPeriod) {

        return Optional.of( source.getKind().accept( new StatementKindVisitor<Statement>() {
                    @Override public Statement visitPayableStatement() {
                        return new PayableStatement()
                                .setType( source.getType())
                                .setEntity( entities.findByEntityIdAndDate(
                                        source.getSettlementEntity().getEntityId(), source.getDate()).orElseThrow())
                                .setSettlementPeriod( settlementPeriod)
                                .setSettlementDate( source.getDate())
                                .setLiability( source.getLiability())
                                .setDue( source.getDue())
                                .setNumber( source.getNumber());
                    }
                    @Override public Statement visitRegisterStatement() {
                        return new RegisterStatement();
                    }
                }).setDate( source.getDate())
                .setStatementId( Optional.ofNullable( source.getStatementId())
                        .orElseGet( UUID::randomUUID))
                .setPatternId( source.getPatternId())
                .setCreated( source.getCreated())
                .setMetric( metrics.findByDate( source.getDate()).orElseThrow())
                .setPeriod( source.getPeriod())
                .setNo( source.getNo())
                .setXML( source.getXml()))
                .map( statement->
                    Optional.ofNullable( source.getStatementLines())
                        .map( lines-> statement.setLines( lines.stream()
                                .map( statementLineDto -> line.from( statementLineDto).setStatement( statement))
                                .collect( Collectors.toList())))
                            .orElseGet( ()-> statement))
                .get();

    }

}
