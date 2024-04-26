package pl.janksiegowy.backend.finances.settlement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingFactory;
import pl.janksiegowy.backend.finances.payment.dto.PaymentDto;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementDto;
import pl.janksiegowy.backend.finances.settlement.SettlementKind.SettlementKindVisitor;
import pl.janksiegowy.backend.period.PeriodFacade;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SettlementFactory implements SettlementKindVisitor<Settlement> {

    private final EntityRepository entities;
    private final ClearingFactory clearing;

    public Settlement from( SettlementDto source) {

        return entities.findByEntityIdAndDate( source.getEntity().getEntityId(), source.getDate())
            .map( entity-> source.getKind().accept( this)
                .setEntityId( entity.getId())
                .setSettlementId( Optional.ofNullable( source.getSettlementId()).orElseGet( UUID::randomUUID))
                .setClearings( source.getClearings().stream().map( clearing::from).collect( Collectors.toList()))
                .setDate( source.getDate())
                .setDue( source.getDue())
                .setNumber( source.getNumber())
                .setAmount( source.getAmount()))
            .orElseThrow();
    }

    public static SettlementDto.Proxy to( PaymentDto payment) {
        return SettlementDto.create()
                .settlementId( payment.getDocumentId())
                .amount( payment.getAmount())
                //.type( DocumentType.valueOf( payment.getType().name()))
                .kind( switch( payment.getType()) {
                    case R -> SettlementKind.C;
                    case E -> SettlementKind.D;})
                .number( payment.getNumber())
                .entity( payment.getEntity())
                .date( payment.getDate())
                .due( payment.getDate())
                .clearings( payment.getClearings());
    }

    @Override public Settlement visitDebit() {
        return new Receivable();
    }

    @Override public Settlement visitCredit() {
        return new Payable();
    }
}
