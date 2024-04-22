package pl.janksiegowy.backend.accounting.template;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface TemplateRepository {
    Template save( Template template);

    Optional<Template> findTemplateByTemplateIdAndDate( UUID templateId, LocalDate date );
    Optional<Template> findByDocumentTypeAndDate( TemplateType type, LocalDate date );
}
