package pl.janksiegowy.backend.finances.settlement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementDto;
import pl.janksiegowy.backend.finances.settlement.SettlementType.SettlementTypeVisitor;
import pl.janksiegowy.backend.period.PeriodFacade;

import java.util.UUID;

@AllArgsConstructor
public class SettlementFactory implements SettlementTypeVisitor<Settlement> {

    private final PeriodFacade periods;
    private final EntityRepository entities;

    public Settlement from( SettlementDto source) {
        return update( source, source.getType().accept( this)
                .setSettlementId( UUID.randomUUID()));
    }

    public Settlement update( SettlementDto source ) {
        return update( source, source.getType().accept( this)
                .setSettlementId( source.getSettlementId()));
    }

    public Settlement update( SettlementDto source, Settlement settlement) {
        entities.findByEntityIdAndDate( source.getEntity().getEntityId(), source.getDate())
                .ifPresent( settlement::setEntity);

        return settlement.setPeriod( periods.findMonthPeriodOrAdd( source.getDate()))
                .setDate( source.getDate())
                .setDue( source.getDue())
                .setKind( source.getKind())
                .setNumber( source.getNumber())
                .setDt( source.getDt())
                .setCt( source.getCt());
    }

    @Override public Settlement visitInvoiceSettlemnt() {
        return new InvoiceSettlement();
    }

    @Override public Settlement visitPaymentSettlement() {
        return new PaymentSettlement();
    }

    @Override public Settlement visitStatementSettlement() {
        return new StatementSettlement();
    }

    @Override public Settlement visitPayslipSettlement() {
        return new PayslipSettlement();
    }

    @Override public Settlement visitChargeSettlement() {
        return new ChargeSettlement();
    }

    @Override public Settlement visitFeeSettlement() {
        return new FeeSettlement();
    }

    @Override public Settlement visitNoteSettlement() {
        return new NoteSettlement();
    }
}
