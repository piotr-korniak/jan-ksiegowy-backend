package pl.janksiegowy.backend.declaration;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.declaration.dto.StatementDto;
import pl.janksiegowy.backend.declaration.DeclarationType.DeclarationTypeVisitor;
import pl.janksiegowy.backend.period.Period;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class StatementFactory {
    private final EntityRepository entities;
    private final MetricRepository metrics;

    public Declaration from( StatementDto source, MonthPeriod settlementPeriod) {
        return metrics.findByDate( source.getDate())
            .map( metric -> source.getType().accept( new DeclarationTypeVisitor <Declaration>() {
                    @Override public Declaration visitVatDeclaration() {
                        return update( new Declaration_VAT(), source, settlementPeriod);
                    }
                    @Override public Declaration visitJpkStatement() {
                        return new Declaration_JPK();
                    }
                    @Override public Declaration visitCitStatement() {
                        return update( new Declaration_CIT(), source, settlementPeriod);
                    }
                    @Override public Declaration visitPitStatement() {
                        return null;
                    }
                    @Override public Declaration visitDraStatement() {
                        return update( new Declaration_DRA(), source, settlementPeriod);
                    }

                }).setDate( source.getDate())
                    .setStatementId( Optional.ofNullable( source.getStatementId()).orElseGet( UUID::randomUUID))
                    .setPatternId( source.getPatternId())
                    .setCreated( source.getCreated())
                    .setMetric( metrics.findByDate( source.getDate()).orElseThrow())
                    .setPeriod( source.getPeriod())
                    .setNo( source.getNo())
                    .setXML( source.getXml())
                    .setElements( source.getElements().entrySet().stream()
                            .filter(entry-> entry.getValue().signum()!= 0)
                            .collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue))))
            .orElseThrow();
    }

    private PayableDeclaration update( PayableDeclaration declaration, StatementDto source, MonthPeriod period) {
        return entities.findByEntityIdAndDate( source.getSettlementEntity().getEntityId(), source.getDate())
                .map( entity-> declaration
                        .setEntity( entity)
                        .setSettlementPeriod( period)
                        .setSettlementDate( source.getDate())
                        .setLiability( source.getLiability())
                        .setDue( source.getDue())
                        .setNumber( source.getNumber()))
                .orElseThrow();
    }

}
