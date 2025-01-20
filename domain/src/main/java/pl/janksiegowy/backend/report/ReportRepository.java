package pl.janksiegowy.backend.report;

import java.util.Optional;

public interface ReportRepository {

    ReportSchema save(ReportSchema report);
    boolean existsByTypeAndCode( ReportType type, String code);
    Optional<ReportSchema> findByTypeAndCode( ReportType type, String code);
}
