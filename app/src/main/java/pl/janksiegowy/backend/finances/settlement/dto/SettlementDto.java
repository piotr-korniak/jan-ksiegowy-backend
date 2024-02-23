package pl.janksiegowy.backend.finances.settlement.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;

import java.util.UUID;

public interface SettlementDto {

    UUID getId();
    String getNumber();

    EntityDto getEntity();


}
