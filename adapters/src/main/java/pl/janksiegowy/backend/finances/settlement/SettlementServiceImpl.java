package pl.janksiegowy.backend.finances.settlement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.janksiegowy.backend.accounting.decree.DecreeQueryRepository;
import pl.janksiegowy.backend.accounting.decree.DecreeRepository;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.payment.PaymentRepository;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementMap;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SettlementServiceImpl implements SettlementService {

    private final ClearingRepository clearingRepository;
    private final SettlementQueryRepository settlements;
    private final SettlementFacade settlementFacade;


    @Transactional( transactionManager= "companyTransactionManager")
    public void deleteSettlement( UUID documentId) {

        settlements.findBySettlementId( documentId)
            .map( SettlementMap::new)
            .ifPresentOrElse(settlement-> {

                settlement.getKind().accept( new SettlementKind.SettlementKindVisitor<List<Clearing>>() {
                    @Override public List<Clearing> visitDebit() {
                        return clearingRepository.receivableId( documentId);
                    }
                    @Override public List<Clearing> visitCredit() {
                        return clearingRepository.payableId( documentId);
                    }
                }).forEach( clearing -> {
                    settlementFacade.save(
                        settlements.findBySettlementId( clearing.getOpposite( documentId))
                            .map( SettlementMap::new)
                            .map( settlementMap-> settlementMap.remove( documentId)).orElseThrow());
                    settlement.remove( clearing.getOpposite( documentId));
                });
                settlementFacade.save( settlement);

            },
            ()-> new NoSuchElementException( "Settlement with the given ID does not exist: "+ documentId));
    }
}
