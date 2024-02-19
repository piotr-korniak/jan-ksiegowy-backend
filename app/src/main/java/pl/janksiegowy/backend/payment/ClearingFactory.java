package pl.janksiegowy.backend.payment;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.payment.dto.ClearingDto;
import pl.janksiegowy.backend.settlement.SettlementRepository;

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
