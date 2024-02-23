package pl.janksiegowy.backend.finances.clearing;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.settlement.SettlementRepository;

@AllArgsConstructor
public class ClearingFactory {

    private final SettlementRepository repository;

    public Clearing from( ClearingDto source ) {

        repository.findByDocument( source.getReceivable());

        return new Clearing()
                .setDate( source.getDate())
                .setSettlement( source.getAmount(),
                    repository.findByDocument( source.getReceivable()).orElseThrow(),
                    repository.findByDocument( source.getPayable()).orElseThrow());
    }
}
