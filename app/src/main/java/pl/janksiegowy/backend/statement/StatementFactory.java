package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.finances.settlement.SettlementType;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.statement.StatementType.StatementTypeVisitor;
import pl.janksiegowy.backend.statement.StatementKind.StatementKindVisitor;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class StatementFactory {
    private final EntityRepository entities;
    private final StatementLineFactory line;
    private final PeriodRepository periods;

    public Statement from( StatementDto source, MonthPeriod settlementPeriod) {

        return Optional.of( source.getKind().accept( new StatementKindVisitor<Statement>() {
                    @Override public Statement visitPayableStatement() {
                        return new PayableStatement()
                                .setType( source.getType())
                                .setEntity( entities.findByEntityIdAndDate(
                                        source.getSettlementEntity().getEntityId(), source.getDate()).orElseThrow())
                                .setSettlementPeriod( settlementPeriod)
                                .setSettlementDate( settlementPeriod.getEnd())
                                .setLiability( source.getLiability())
                                .setDue( source.getDue())
                                .setNumber( source.getNumber());
                                //.setValue_1( source.getValue1())
                                //.setValue_2( source.getValue2());
                    }
                    @Override public Statement visitRegisterStatement() {
                        return new RegisterStatement();
                    }
                }).setDate( source.getDate())
                .setStatementId( Optional.ofNullable( source.getStatementId())
                        .orElseGet( UUID::randomUUID))
                .setPatternId( source.getPatternId())
                .setCreated( source.getCreated())
                //.setMetric( metrics.findByDate( source.getInvoiceDate()).orElseThrow())

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
/*
        return periods.findById( source.getPeriodId()).map( period-> source.getType()
            .accept( new StatementTypeVisitor<Statement>() {
                @Override public Statement visitVatStatement() {
                    return new PayableStatement().setLiability( source.getLiability())
                            .setSettlementType( SettlementType.V)
                            .setKind( SettlementKind.C)
                            .setDue( source.getDue())
                            .setSettlementDate( source.getDate())
                            .setSettlementPeriod( settlementPeriod)
                            .setNumber( source.getNumber())
                            .setEntity( entities.findByEntityIdAndDate(
                                    source.getSettlementEntity().getEntityId(), source.getDate()).orElseThrow())
                            .setValue_1( source.getValue1())
                            .setValue_2( source.getValue2());
                }

            }).setDate( source.getDate())
                        .setStatementId( Optional.ofNullable( source.getStatementId())
                                .orElseGet( UUID::randomUUID))
                        .setPatternId( source.getPatternId())
                        .setCreated( source.getCreated())
                        //.setMetric( metrics.findByDate( source.getInvoiceDate()).orElseThrow())
                        .setPeriod( period)
                        .setNo( source.getNo())
                        .setXML( source.getXml()))
                .orElseThrow();
*/
    }

}
