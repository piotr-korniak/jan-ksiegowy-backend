package pl.janksiegowy.backend.contract;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.salary.contract.Contract;
import pl.janksiegowy.backend.salary.ContractRepository;
import pl.janksiegowy.backend.contract.dto.ContractDto;
import pl.janksiegowy.backend.shared.MigrationService;


@Log4j2

@AllArgsConstructor
public class ContractFacade {

    private final ContractFactory contractFactory;
    private final ContractRepository contractRepository;
    private final ContractQueryRepository contracts;
    private final EntityQueryRepository entities;
    private final MigrationService migrationService;

    public Contract save(ContractDto source) {
        return contractRepository.save( contractFactory.from( source));
    }

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadContracts()
            .forEach( contractDto-> {
                counters[0]++;

                if( !contracts.existsByNumber( contractDto.getNumber())) {
                    entities.findByTypeAndTaxNumber( EntityType.E, contractDto.getTaxNumber())
                        .ifPresent( entity-> {
                            save( contractDto.entity(entity));
                            counters[1]++;
                        });
                }
            });
        log.warn( "Contracts migration complete!");
        return "%-40s %16s".formatted( "Contracts migration complete, added: ", counters[1]+ "/"+ counters[0]);
    }
}
