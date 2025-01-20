package pl.janksiegowy.backend.salary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.contract.ContractFacade;
import pl.janksiegowy.backend.contract.ContractFactory;
import pl.janksiegowy.backend.contract.ContractQueryRepository;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.shared.MigrationService;

@Configuration
public class ContractConfiguration {

    @Bean
    ContractFacade contractFacade( final EntityQueryRepository entities,
                                   final EntityRepository entityRepository,
                                   final ContractRepository contractRepository,
                                   final ContractQueryRepository contracts,
                                   final MigrationService migrationService
                                  ) {
        return new ContractFacade( new ContractFactory( entityRepository),
                contractRepository, contracts, entities, migrationService);
    }
}
