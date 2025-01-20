package pl.janksiegowy.backend.report;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.shared.MigrationService;

@Configuration
public class ReportConfiguration {

    @Bean
    public ReportFacade reportFacade( final MigrationService migrationService,
                                      final ReportRepository reportRepository,
                                      final ReportQueryRepository reports,
                                      final DecreeLineQueryRepository decreeLines) {
        return new ReportFacade( migrationService, new ReportFactory(), reportRepository, reports, decreeLines);

    }
}
