package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.salary.dto.ContractDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class ContractInitializer {

    private final ContractQueryRepository contracts;
    private final EntityQueryRepository entities;
    private final ContractFacade contract;

    public String init( List<ContractDto> initialContracts) {

        var added= new AtomicInteger( 0);

        initialContracts.forEach( contractDto-> {
            if( !contracts.existsByNumber( contractDto.getNumber())){
                contract.save( contractDto);
                added.incrementAndGet();
            }
        });
        return String.format( "%-30s %16s\n", "Contracts migration complete, added: ",
                added+ "/"+ initialContracts.size());
    }
}
