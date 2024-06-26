package pl.janksiegowy.backend.finances.settlement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementDto;

import java.util.Optional;

@AllArgsConstructor
public class SettlementFacade {

    private final SettlementFactory settlement;
    private final SettlementRepository settlements;

    public Settlement save( SettlementDto source) {
        return settlements.save( settlement.from( source));
    }
}
