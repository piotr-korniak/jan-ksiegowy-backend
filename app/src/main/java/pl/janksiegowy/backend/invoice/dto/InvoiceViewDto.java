package pl.janksiegowy.backend.invoice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import pl.janksiegowy.backend.entity.dto.EntityViewDto;

import java.math.BigDecimal;

public interface InvoiceViewDto {


/*
    @JsonProperty( "InvoiceNumber")
    String getSettlementNumber();

    //String getSettlementEntityName();
    @JsonProperty( "Contact")
    EntityViewDto getSettlementEntity();
*/

    @Value("#{target.getAmountDue()}")
    Integer getAmountDue();
}
