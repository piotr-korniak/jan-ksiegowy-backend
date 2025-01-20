package pl.janksiegowy.backend.finances.settlement.dto;

import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;
import pl.janksiegowy.backend.finances.settlement.SettlementType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SettlementMap implements SettlementDto {

    private final SettlementDto settlement;
    private final List<ClearingDto> clearings;

    public SettlementMap( SettlementDto settlement) {
        this.settlement= settlement;
        this.clearings= new ArrayList<>( settlement.getClearings());
    }

    public ClearingDto add( ClearingDto clearing) {
        clearings.add( clearing);
        return clearing;
    }

    public SettlementMap remove( UUID dokumentId) {
        clearings.removeIf(clearing ->
                dokumentId.equals( clearing.getReceivableId()) || dokumentId.equals( clearing.getPayableId()));
        return this;
    }

    @Override public UUID getSettlementId() {
        return settlement.getSettlementId();
    }
    @Override public SettlementType getType() {
        return settlement.getType();
    }
    @Override public SettlementKind getKind() {
        return settlement.getKind();
    }
    @Override public String getNumber() {
        return settlement.getNumber();
    }
    @Override public LocalDate getDate() {
        return settlement.getDate();
    }
    @Override public LocalDate getDue() {
        return settlement.getDue();
    }
    @Override public EntityDto getEntity() {
        return settlement.getEntity();
    }
    @Override public BigDecimal getDt() {
        return settlement.getDt();
    }
    @Override public BigDecimal getCt() {
        return settlement.getCt();
    }
    @Override public BigDecimal getAmount() {
        return settlement.getAmount();
    }
    @Override public List<ClearingDto> getClearings() {
        return clearings;
    }
}
