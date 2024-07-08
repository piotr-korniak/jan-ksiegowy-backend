package pl.janksiegowy.backend.finances.charge;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.charge.dto.ChargeDto;
import pl.janksiegowy.backend.period.PeriodFacade;


import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ChargeFactory implements ChargeType.ChargeTypeVisitor<Charge> {
    private final EntityRepository entities;
    private final PeriodFacade periods;

    public Charge from( ChargeDto source ) {

        return (Charge) source.getType().accept( this)
                .setNumber( source.getNumber())
                .setIssueDate( source.getDate())
                .setDueDate( source.getDue())
                .setAmount( source.getAmount())
                .setDocumentId( Optional.ofNullable( source.getChargeId())
                        .orElseGet( UUID::randomUUID))
                .setPeriod( periods.findMonthPeriodOrAdd( source.getDate()))
                .setEntity( entities.findByEntityIdAndDate(
                        source.getEntity().getEntityId(), source.getDate()).orElseThrow());
    }

    @Override public Charge visitFeeCharge() {
        return new FeeCharge();
    }

    @Override public Charge visitCharge() {
        return new LevyCharge();
    }
}
