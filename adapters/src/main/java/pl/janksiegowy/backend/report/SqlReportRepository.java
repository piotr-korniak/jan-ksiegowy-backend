package pl.janksiegowy.backend.report;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface SqlReportRepository extends JpaRepository<ReportSchema, UUID> {

    boolean existsByTypeAndCode( ReportType type, String code);
    Optional<ReportSchema> findByTypeAndCode( ReportType type, String code);
}

interface SqlReportQueryRepository extends ReportQueryRepository, Repository<ReportSchema, UUID> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class RaportRepositoryImpl implements ReportRepository {

    private final SqlReportRepository reportRepository;

    @Override public ReportSchema save(ReportSchema report) {
        return reportRepository.save( report);
    }

    @Override public boolean existsByTypeAndCode( ReportType type, String code) {
        return reportRepository.existsByTypeAndCode( type, code);
    }

    @Override
    public Optional<ReportSchema> findByTypeAndCode( ReportType type, String code) {
        return reportRepository.findByTypeAndCode( type, code);
    }
}