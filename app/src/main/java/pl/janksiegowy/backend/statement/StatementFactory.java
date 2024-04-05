package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.statement.StatementType.StatementTypeVisitor;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class StatementFactory implements StatementTypeVisitor<StatementDocument> {
    private final EntityRepository entities;
    private final PeriodRepository periods;

    public Statement from( StatementDto source) {

        var period= periods.findById( source.getPeriodId()).orElseThrow();
        return Optional.ofNullable( source.getLiability())
                .map( liability-> source.getType().accept( this)
                    .setLiability( source.getLiability())
                    .setNumber( source.getNumber())
                    .setDue( source.getDue())
                    .setStatementId( Optional.ofNullable( source.getStatementId()).orElseGet( UUID::randomUUID))
                    .setValue_1( source.getValue1())
                    .setValue_2( source.getValue2())
/*                    .setSettlement( (StatementSettlement)new StatementSettlement()
                        .setDate( source.getDate())
                        .setNumber( source.getSettlementNumber())
                        .setEntity( entities.findByEntityIdAndDate(
                                source.getSettlementEntity().getEntityId(), source.getDate()).orElseThrow())
                        .setDue( source.getSettlementDue())
                        .setCt( liability)
                        .setKind( SettlementKind.C)
                        .setPeriod(  periods.findMonthByDate( period.getEnd()).orElseThrow()))*/)
                .orElse( source.getType().accept( this)
                        .setStatementId( Optional.ofNullable( source.getStatementId()).orElseGet( UUID::randomUUID)))
                    .setPattern( source.getPatternId())
                    .setDate( source.getDate())
                    .setCreated( source.getCreated())
                    .setPeriod( period)
                    .setXML( source.getXml());

    }

    @Override public StatementDocument visitVatStatement() {
        return null;//new VatStatement();
    }

    @Override public StatementDocument visitCitStatement() {
        return new CitStatement();
    }

    @Override
    public StatementDocument visitPitStatement() {
        return null;
    }

    @Override
    public StatementDocument visitZusStatement() {
        return null;
    }
}
