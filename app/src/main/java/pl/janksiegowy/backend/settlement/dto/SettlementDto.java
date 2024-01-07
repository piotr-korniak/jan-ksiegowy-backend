package pl.janksiegowy.backend.settlement.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

public interface SettlementDto {

    String getNumber();

    /*
    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements SettlementDto {

    }*/
}
