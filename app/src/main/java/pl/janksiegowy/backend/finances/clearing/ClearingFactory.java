package pl.janksiegowy.backend.finances.clearing;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.settlement.SettlementRepository;

@AllArgsConstructor
public class ClearingFactory {

    private final SettlementRepository repository;

    public Clearing from( ClearingDto source ) {

        repository.findByDocument( source.getReceivableId());

        return new Clearing()
                .setDate( source.getDate())
                .setSettlement( source.getAmount(), source.getReceivableId(), source.getPayableId());
                //.setSettlement( source.getAmount(),
                //    repository.findByDocument( source.getReceivable()).orElseThrow(),
                //    repository.findByDocument( source.getPayable()).orElseThrow());
    }

    public ClearingDto to( Clearing clearing) {
        return ClearingDto.create()
                .date( clearing.getDate())
                .amount( clearing.getAmount())
                .receivable( clearing.getReceivableId())
                .payable( clearing.getPayableId());
    }
}
