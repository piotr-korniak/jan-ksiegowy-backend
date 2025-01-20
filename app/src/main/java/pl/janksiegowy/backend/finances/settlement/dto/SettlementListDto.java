package pl.janksiegowy.backend.finances.settlement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.settlement.SettlementType;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SettlementListDto {

    String getNumber();
    EntityType getEntityType();
    String getEntityName();
    String getEntityAccountNumber();

    @JsonFormat( pattern= "yyyy-MM-dd")
    LocalDate getDate();

    @JsonFormat( pattern= "yyyy-MM-dd")
    LocalDate getDue();
    BigDecimal getDt();
    BigDecimal getCt();
    SettlementType getType();
}
