package pl.janksiegowy.backend.finances.settlement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.clearing.ClearingFactory;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementDto;
import pl.janksiegowy.backend.finances.settlement.SettlementKind.SettlementKindVisitor;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SettlementFactory implements SettlementKindVisitor<Settlement> {

    private final EntityRepository entities;
    private final ClearingFactory clearings;

    public Settlement from( SettlementDto source) {

        return entities.findByEntityIdAndDate( source.getEntity().getEntityId(), source.getDate())
            .map( entity-> source.getKind().accept( this)
                .setEntityId( entity.getId())
                .setSettlementId( Optional.ofNullable( source.getSettlementId()).orElseGet( UUID::randomUUID))
                .setClearings( source.getClearings().stream().map( clearings::from).collect( Collectors.toList()))
                .setDate( source.getDate())
                .setDue( source.getDue())
                .setNumber( source.getNumber())
                .setAmount( source.getAmount()))
            .orElseThrow();
    }

    public SettlementDto.Proxy to( Payment payment) {
        return SettlementDto.create()
                .settlementId( payment.getDocumentId())
                .amount( payment.getAmount())
                //.type( DocumentType.valueOf( payment.getType().name()))
                .kind( switch( payment.getType()) {
                    case R -> SettlementKind.C;
                    case E -> SettlementKind.D;})
                .number( payment.getNumber())
                .entity( EntityDto.create()
                        .entityId( payment.getEntity().getEntityId()))
                .date( payment.getIssueDate())
                .due( payment.getIssueDate())
                .clearings( payment.getClearings().stream()
                        .map( clearings::to )
                        .collect( Collectors.toList()));
    }

    @Override public Settlement visitDebit() {
        return new Receivable();
    }

    @Override public Settlement visitCredit() {
        return new Payable();
    }
}
