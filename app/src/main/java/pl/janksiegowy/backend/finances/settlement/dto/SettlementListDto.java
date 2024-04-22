package pl.janksiegowy.backend.finances.settlement.dto;

import pl.janksiegowy.backend.finances.settlement.SettlementType;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SettlementListDto {

    String getNumber();
    String getEntityAccountNumber();
    String getEntityName();
    LocalDate getDate();
    LocalDate getDue();
    BigDecimal getDt();
    BigDecimal getCt();
    SettlementType getType();
}
