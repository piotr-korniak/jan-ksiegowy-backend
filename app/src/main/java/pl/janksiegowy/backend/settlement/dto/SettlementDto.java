package pl.janksiegowy.backend.settlement.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

public interface SettlementDto {

    UUID getId();
    String getNumber();


}
