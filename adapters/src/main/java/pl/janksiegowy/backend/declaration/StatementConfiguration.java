package pl.janksiegowy.backend.declaration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.metric.MetricRepository;

@Configuration
public class StatementConfiguration {

    @Bean
    StatementFacade statementFacade(final StatementRepository statements,
                                    final EntityRepository entities,
                                    final MetricRepository metrics,
                                    final StatementService statementService,
                                    final DecreeFacade decree) {
        return new StatementFacade( new StatementFactory( entities, metrics),
                statements, statementService, decree);
    }

}
