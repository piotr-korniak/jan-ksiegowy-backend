package pl.janksiegowy.backend.salary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityRepository;

import java.util.List;

@Configuration
public class ContractConfiguration {

    @Bean
    ContractFacade contractFacade( final ContractRepository contracts,
                                   final EntityQueryRepository entities,
                                   final EntityRepository entity) {
        return new ContractFacade( new ContractFactory( entities, entity), contracts);
    }
}
