package pl.janksiegowy.backend.finances.settlement;

import java.util.UUID;


public interface SettlementService {

    void deleteSettlement( UUID documentId);
}
