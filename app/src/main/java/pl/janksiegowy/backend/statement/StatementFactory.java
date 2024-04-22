package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.finances.settlement.SettlementType;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.statement.StatementType.StatementTypeVisitor;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class StatementFactory {
    private final EntityRepository entities;
    private final PeriodRepository periods;

    public Statement from( StatementDto source, MonthPeriod settlementPeriod) {

        return periods.findById( source.getPeriodId()).map( period-> source.getType()
            .accept( new StatementTypeVisitor<Statement>() {
                @Override public Statement visitVatStatement() {
                    return new VatStatement().setLiability( source.getLiability())
                            .setSettlementType( SettlementType.S)
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
                @Override public Statement visitJpkStatement() {
                    return new JpkStatement();  // without settlement
                }
                @Override public Statement visitCitStatement() {
                    return new CitStatement().setLiability( source.getLiability());
                }

                @Override public Statement visitPitStatement() {
                    return null;
                }
                @Override public Statement visitZusStatement() {
                    return null;
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

/*

        ).orElseThrow();
        return Optional.ofNullable( source.getLiability())
                .map( liability-> source.getType().accept( this)
                    .setLiability( source.getLiability())
                    .setNumber( source.getNumber())
                    .setDue( source.getDue())
                    .setStatementId( Optional.ofNullable( source.getStatementId()).orElseGet( UUID::randomUUID))
                    .setValue_1( source.getValue1())
                    .setValue_2( source.getValue2())
//                    .setSettlement( (StatementSettlement)new StatementSettlement()
//                        .setDate( source.getDate())
//                        .setNumber( source.getSettlementNumber())
//                        .setEntity( entities.findByEntityIdAndDate(
//                                source.getSettlementEntity().getEntityId(), source.getDate()).orElseThrow())
//                        .setDue( source.getSettlementDue())
//                        .setCt( liability)
//                        .setKind( SettlementKind.C)
//                        .setPeriod(  periods.findMonthByDate( period.getEnd()).orElseThrow()))
                .orElse( source.getType().accept( this)
                        .setStatementId( Optional.ofNullable( source.getStatementId()).orElseGet( UUID::randomUUID)))
                    .setPattern( source.getPatternId())
                    .setDate( source.getDate())
                    .setCreated( source.getCreated())
                    .setPeriod( period)
                    .setXML( source.getXml());
*/
    }

}
