package pl.janksiegowy.backend.statement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.tax.TaxCalculatorManager;

import java.util.List;

@Configuration
public class StatementConfiguration {

    @Bean
    StatementFacade statementFacade(final StatementRepository statements,
                                    final EntityRepository entities,
                                    final PeriodRepository periods,
                                    final DecreeFacade decree,
                                    final List<Factory_JPK_V7> factoriesJpkV7,
                                    final TaxCalculatorManager taxManager) {
        return new StatementFacade(
                new StatementFactory( entities, new StatementLineFactory(), periods),
                statements, decree, factoriesJpkV7, taxManager);
    }

}
