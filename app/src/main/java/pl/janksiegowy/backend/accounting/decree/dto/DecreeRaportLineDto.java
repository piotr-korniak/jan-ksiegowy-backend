package pl.janksiegowy.backend.accounting.decree.dto;

import java.math.BigDecimal;

public interface DecreeRaportLineDto {

    String getLabel();
    BigDecimal getAmount();
}
