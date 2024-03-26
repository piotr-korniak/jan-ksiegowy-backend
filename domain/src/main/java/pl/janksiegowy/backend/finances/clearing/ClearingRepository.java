package pl.janksiegowy.backend.finances.clearing;

import pl.janksiegowy.backend.finances.settlement.Settlement;
import pl.janksiegowy.backend.statement.StatementType;

import java.util.List;
import java.util.stream.Stream;

public interface ClearingRepository {

    Clearing save( Clearing clearing);
    List<Clearing> receivable( Settlement settlement);
    List<Clearing> payable( Settlement settlement);

}
