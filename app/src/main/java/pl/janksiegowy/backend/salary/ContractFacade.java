package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.salary.dto.ContractDto;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ContractFacade {

    private final ContractFactory contract;
    private final ContractRepository contracts;




    Contract save( ContractDto source) {
        return contracts.save( Optional.ofNullable( source.getContractId())
                .map( uuid-> contract.update( source))
                .orElseGet( ()-> contract.from( source)));
    }
}
