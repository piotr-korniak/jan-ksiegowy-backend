package pl.janksiegowy.backend.finances.clearing;

import java.util.List;
import java.util.UUID;

public interface ClearingRepository {

    Clearing save( Clearing clearing);

    List<Clearing> receivableId( UUID settlementId);
    List<Clearing> payableId( UUID settlementId);

    void delete( Clearing clearing);

}
