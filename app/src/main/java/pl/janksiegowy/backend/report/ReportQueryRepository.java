package pl.janksiegowy.backend.report;

import java.util.Optional;

public interface ReportQueryRepository {

    <T> Optional<T> findByTypeAndCode(Class<T> klass, ReportType type, String number);
}
