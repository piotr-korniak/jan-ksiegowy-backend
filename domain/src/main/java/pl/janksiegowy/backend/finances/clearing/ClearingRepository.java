package pl.janksiegowy.backend.finances.clearing;

import pl.janksiegowy.backend.finances.settlement.Settlement;

import java.util.List;
import java.util.UUID;

public interface ClearingRepository {

    Clearing save( Clearing clearing);
    List<Clearing> receivableId( UUID settlementId);
    List<Clearing> payableId( UUID settlementId);

}
