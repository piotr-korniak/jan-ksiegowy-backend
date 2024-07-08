package pl.janksiegowy.backend.finances.settlement.dto;

import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.settlement.SettlementType;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SettlementListDto {

    String getNumber();
    EntityType getEntityType();
    String getEntityName();
    String getEntityAccountNumber();
    LocalDate getDate();
    LocalDate getDue();
    BigDecimal getDt();
    BigDecimal getCt();
    SettlementType getType();
}
